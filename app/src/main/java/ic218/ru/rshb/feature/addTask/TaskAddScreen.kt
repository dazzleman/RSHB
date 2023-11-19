package ic218.ru.rshb.feature.addTask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import ic218.ru.rshb.R
import ic218.ru.rshb.domain.entity.TaskField
import ic218.ru.rshb.manager.SYNC_DB_WORK
import ic218.ru.rshb.manager.syncWorkOneRequest
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title3MediumStyle
import ic218.ru.rshb.ui.theme.title4NormalStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskAddScreen(
    navController: NavHostController,
    searchResult: TaskAddItemType? = null,
    viewModel: TaskAddViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    Column(
        modifier = Modifier
            .padding(top = 26.dp)
            .fillMaxSize()
    ) {
        Title { navController.popBackStack() }
        Operations(
            viewModel = viewModel,
            onClick = {
                navController.navigate("search/${it.desc}/${it.type.ordinal}")
            })
    }

    LaunchedEffect(searchResult) {
        viewModel.selectedType(searchResult)
    }

    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.navigateToChannel.collect {
                when (it) {
                    is AddTaskCommand.Back -> {
                        navController.popBackStack()
                    }

                    is AddTaskCommand.SyncDb -> {
                        WorkManager.getInstance(context.applicationContext)
                            .enqueueUniqueWork(
                                SYNC_DB_WORK,
                                ExistingWorkPolicy.APPEND_OR_REPLACE,
                                syncWorkOneRequest
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun Title(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 30.dp)
                .size(30.dp)
                .clickable { onClick() },
            painter = painterResource(id = R.drawable.ic_back_ios),
            contentDescription = null,
            tint = Color(0xFF8AE1F9)
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "НОВАЯ ЗАДАЧА",
            style = headerBigBoldStyle
        )
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun Operations(
    viewModel: TaskAddViewModel,
    onClick: (TaskAddItemType) -> Unit,
) {
    val items = viewModel.items
    val enabledCreateTask = viewModel.enabledCreateTask

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items.forEachIndexed { index, type ->
            when (type.type) {
                TaskField.Type.CUSTOM -> {
                    val isLast = index == items.lastIndex
                    CustomOperationItem(type, isLast) {
                        viewModel.changeCustomValue(it, type)
                    }
                }

                else -> OperationItem(type, onClick)
            }
        }

        if (items.size > 1) {
            Button(
                modifier = Modifier
                    .padding(top = 30.dp, start = 15.dp, end = 15.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(47.dp),
                enabled = enabledCreateTask,
                shape = RoundedCornerShape(size = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9C6F2C),
                    disabledContainerColor = Color(0x809C6F2C)
                ),
                onClick = {
                    viewModel.startTask()
                }) {
                Text(
                    text = "Создать задачу",
                    style = title3MediumStyle,
                )
            }
        }
    }
}

@Composable
fun OperationItem(
    type: TaskAddItemType,
    onClick: (TaskAddItemType) -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 23.dp, top = 15.dp),
            text = type.desc,
            style = title4NormalStyle
        )

        Row(
            modifier = Modifier
                .padding(top = 2.dp, start = 15.dp, end = 15.dp)
                .height(50.dp)
                .fillMaxWidth()
                .background(color = Color(0x1AFFFFFF), shape = RoundedCornerShape(size = 5.dp))
                .padding(start = 15.dp, end = 15.dp)
                .clickable {
                    onClick(type)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when {
                    type.name.isEmpty() -> type.hint
                    else -> type.name
                },
                style = title4NormalStyle,
                color = when {
                    type.name.isEmpty() -> Color(0x80FFFFFF)
                    else -> Color(0xFF8AE159)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = Color(0xFF969696),
                contentDescription = "image description",
            )
        }
    }
}

@Composable
fun CustomOperationItem(
    type: TaskAddItemType,
    isLast: Boolean,
    onChangeValue: (String) -> Unit
) {
    var text by remember { mutableStateOf(String()) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 23.dp, top = 15.dp),
            text = type.desc,
            style = title4NormalStyle
        )

        if (type.subDesc.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 23.dp),
                text = type.subDesc,
                style = title4NormalStyle,
                color = Color(0x80FFFFFF),
            )
        }

        val keyboardType = when (type.customParam) {
            TaskField.CustomParam.SPEED -> KeyboardType.Phone
            TaskField.CustomParam.DEPTH -> KeyboardType.Phone
            TaskField.CustomParam.SOLVENT -> KeyboardType.Phone
            else -> KeyboardType.Text
        }

        Box(
            modifier = Modifier
                .padding(top = 2.dp, start = 15.dp, end = 15.dp)
                .height(50.dp)
                .fillMaxWidth()
                .background(color = Color(0x1AFFFFFF), shape = RoundedCornerShape(size = 5.dp)),
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = text,
                placeholder = {
                    Text(
                        text = "Введите...",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0x59FFFFFF),
                        )
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = false,
                    keyboardType = keyboardType,
                    imeAction = when {
                        isLast -> ImeAction.Done
                        else -> ImeAction.Next
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFF8AE159),
                    focusedTextColor = Color(0xFF8AE159),
                    unfocusedTextColor = Color(0xFF8AE159)
                ),
                shape = RectangleShape,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF8AE159),
                ),
                onValueChange = {
                    text = it
                    onChangeValue.invoke(it)
                }
            )
        }
    }
}