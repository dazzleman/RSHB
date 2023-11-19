package ic218.ru.rshb.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import ic218.ru.rshb.R
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title2SlimStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = koinViewModel()
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.navigateToChannel.collect {
                when (it) {
                    is SplashScreenNavItem.Auth -> {
                        navController.navigate("auth") {
                            popUpTo(0) {
                                inclusive = true
                                saveState = false
                            }
                        }
                    }

                    is SplashScreenNavItem.Main -> {
                        navController.navigate("main") {
                            popUpTo(0) {
                                inclusive = true
                                saveState = false
                            }
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Image(
                modifier = Modifier
                    .padding(top = 37.dp, start = 28.dp, end = 28.dp)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_splash_top),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )

            Text(
                modifier = Modifier
                    .padding(top = 59.dp, start = 33.dp, end = 33.dp)
                    .fillMaxWidth(),
                text = "Добро пожаловать в РСХБ-МЕХА.",
                style = headerBigBoldStyle
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp, start = 33.dp, end = 33.dp)
                    .fillMaxWidth(),
                text = "Отслеживайте актуальные задачи и оптимизируйте своё рабочее время.",
                style = title2SlimStyle
            )
        }

        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.ic_splash_psheno),
            contentDescription = null
        )
    }
}