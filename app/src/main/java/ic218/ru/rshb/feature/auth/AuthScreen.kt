package ic218.ru.rshb.feature.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import ic218.ru.rshb.R
import ic218.ru.rshb.manager.SYNC_DB_PERIODIC
import ic218.ru.rshb.manager.syncPeriodicOneRequest
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title2SlimStyle
import ic218.ru.rshb.ui.theme.title3NormalStyle
import ic218.ru.rshb.ui.theme.title4NormalStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val stateUi by viewModel.uiState.collectAsState()

    AuthScreenContent(stateUi)

    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.navigateToChannel.collect {
                when (it) {
                    is AuthCommand.NavToMain -> {
                        navController.navigate("main") {
                            popUpTo(0) {
                                inclusive = true
                                saveState = false
                            }
                        }
                    }

                    is AuthCommand.SyncDb -> {
                        WorkManager.getInstance(context.applicationContext)
                            .enqueueUniquePeriodicWork(SYNC_DB_PERIODIC, ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, syncPeriodicOneRequest)
                    }
                }
            }
        }
    }

    DisposableEffect(lifecycle) {
        val listener = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> viewModel.startNfc()
                Lifecycle.Event.ON_PAUSE -> viewModel.stopNfc()
                else -> Unit
            }
        }
        lifecycle.addObserver(listener)
        onDispose {
            lifecycle.removeObserver(listener)
        }
    }
}

@Composable
private fun AuthScreenIdle() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun AuthScreenContent(stateUi: AuthScreenState) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .padding(top = 39.dp, start = 33.dp, end = 33.dp)
                .fillMaxWidth(),
            text = "Для входа отсканируйте свою персональную карту",
            style = headerBigBoldStyle
        )

        Text(
            modifier = Modifier
                .padding(top = 8.dp, start = 33.dp, end = 33.dp)
                .fillMaxWidth(),
            text = "Поднесите карту к задней части смартфона для сканирования NFC метки.",
            style = title3NormalStyle
        )

        Circle(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 34.dp, start = 67.dp, end = 67.dp)
                .weight(1f),
            stateUi = stateUi
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.ic_splash_psheno),
            contentDescription = null
        )
    }
}

@Composable
fun Circle(
    stateUi: AuthScreenState,
    modifier: Modifier = Modifier
) {


    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (val state = stateUi) {
            is AuthScreenState.Start -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(maxHeight, maxHeight),
                    color = Color(0x801F360D),
                    strokeWidth = 16.dp,
                    trackColor = Color(0xFF8AE159)
                )

                Text(
                    modifier = Modifier,
                    text = "Поиск карты",
                    style = title2SlimStyle,
                )
            }

            is AuthScreenState.AuthNetwork -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(maxHeight, maxHeight),
                    color = Color(0x801F360D),
                    strokeWidth = 16.dp,
                    trackColor = Color(0xFF8AE159)
                )

                Text(
                    modifier = Modifier,
                    text = "Авторизация",
                    style = title2SlimStyle
                )
            }

            is AuthScreenState.Error,
            AuthScreenState.ErrorAuth -> {
                Box(
                    modifier = Modifier
                        .size(maxHeight, maxHeight)
                        .border(width = 16.dp, color = Color(0x801F360D), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_error_circle),
                            contentDescription = null,
                            tint = Color(0xFF8AE159)
                        )

                        val errorText = if (stateUi is AuthScreenState.ErrorAuth) {
                            "Ошибка авторизации\nна сервере."
                        } else {
                            "Не удалось считать карту.\nПопробуйте еще раз."
                        }
                        Text(
                            modifier = Modifier
                                .padding(top = 3.dp),
                            text = errorText,
                            style = title4NormalStyle
                        )
                    }
                }
            }

            is AuthScreenState.Done -> {
                Box(
                    modifier = Modifier
                        .size(maxHeight, maxHeight)
                        .border(width = 16.dp, color = Color(0xFF8AE159), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(100.dp, 82.dp),
                        painter = painterResource(id = R.drawable.ic_auth_complete),
                        contentDescription = null,
                        tint = Color(0xFF8AE159)
                    )
                }
            }

            else -> Unit
        }
    }
}