package ic218.ru.rshb.feature.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ic218.ru.rshb.R
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title3BoldStyle
import ic218.ru.rshb.ui.theme.title3MediumStyle
import ic218.ru.rshb.ui.theme.title3NormalStyle
import ic218.ru.rshb.ui.utils.getTaskPriorityColor
import ic218.ru.rshb.ui.utils.getTaskStatusTitle
import okhttp3.internal.http2.Header
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasksScreen(
    navController: NavHostController,
    viewModel: TasksViewModel = koinViewModel(),
    onClickTask: (Int) -> Unit
) {
    val stateUi by viewModel.uiState.collectAsState()

    TasksScreenMain(
        stateUi = stateUi,
        viewModel = viewModel,
        onClickAdd = {
            navController.navigate("task_add")
        },
        onClickTask = onClickTask
    )
}

@Suppress("NonSkippableComposable")
@Composable
private fun TasksScreenMain(
    stateUi: TasksScreenState,
    viewModel: TasksViewModel,
    onClickAdd: () -> Unit,
    onClickTask: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(viewModel, onClickAdd)
        TopButtonSlider(viewModel)

        when (stateUi) {
            is TasksScreenState.Loading -> TasksLoading()
            is TasksScreenState.Main -> TasksList(viewModel, onClickTask)
        }
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun Header(
    viewModel: TasksViewModel,
    onClickAdd: () -> Unit,
) {
    val addVisible = viewModel.addTasksVisible

    Box(
        modifier = Modifier
            .padding(top = 26.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "ЗАДАЧИ",
            style = headerBigBoldStyle
        )

        if (addVisible) {
            Icon(
                modifier = Modifier
                    .padding(end = 25.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onClickAdd.invoke()
                    },
                painter = painterResource(id = R.drawable.ic_add_fill),
                contentDescription = null,
                tint = Color(0xFF8AE159)
            )
        }
    }
}

@Suppress("NonSkippableComposable")
@Composable
private fun TopButtonSlider(viewModel: TasksViewModel) {
    Box(
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .background(color = Color(0x1AFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            var selected by rememberSaveable {
                mutableStateOf(TopButtonSelected.FIRST)
            }

            Button(
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(size = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (selected) {
                        TopButtonSelected.FIRST -> Color(0x4DFFFFFF)
                        TopButtonSelected.SECOND -> Color.Transparent
                    },
                    contentColor = Color(0xFFFFFFFF)
                ),
                contentPadding = PaddingValues(),
                onClick = {
                    selected = TopButtonSelected.FIRST
                    viewModel.selectOpenClosedTaskButton(selected)
                }
            ) {
                Text(
                    text = "Открытые",
                    style = title3NormalStyle,
                    color = when (selected) {
                        TopButtonSelected.FIRST -> Color(0xFFFFFFFF)
                        TopButtonSelected.SECOND -> Color(0x80FFFFFF)
                    }
                )
            }

            Spacer(modifier = Modifier.width(11.dp))

            Button(
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(size = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (selected) {
                        TopButtonSelected.FIRST -> Color.Transparent
                        TopButtonSelected.SECOND -> Color(0x4DFFFFFF)
                    },
                    contentColor = Color(0xFFFFFFFF)
                ),
                contentPadding = PaddingValues(),
                onClick = {
                    selected = TopButtonSelected.SECOND
                    viewModel.selectOpenClosedTaskButton(selected)
                }
            ) {
                Text(
                    text = "Завершенные",
                    style = title3NormalStyle,
                    color = when (selected) {
                        TopButtonSelected.FIRST -> Color(0x80FFFFFF)
                        TopButtonSelected.SECOND -> Color(0xFFFFFFFF)
                    },
                )
            }
        }
    }
}

enum class TopButtonSelected {
    FIRST, SECOND
}

@Composable
fun TasksLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun TasksList(
    viewModel: TasksViewModel,
    onClickTask: (Int) -> Unit
) {
    val tasks = viewModel.tasks

    if (tasks.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Здесь пока нет задач",
                style = title3MediumStyle
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tasks) { task ->
                TaskItemList(task, onClickTask)
            }
        }
    }
}

@Composable
fun TaskItemList(
    task: Task,
    onClickTask: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .background(color = Color(0x3359779C), shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .clickable { onClickTask.invoke(task.id) }
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight()
                .background(getTaskPriorityColor(task.priority))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0x3359779C))
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#${task.id}",
                    style = title3MediumStyle
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f),
                    text = getTaskStatusTitle(task.status),
                    style = title3BoldStyle,
                    color = Color(0xFF8AE159)
                )

                Text(
                    text = task.operation,
                    style = title3MediumStyle
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Поле: ${task.pole}",
                    style = title3MediumStyle
                )

                Text(
                    text = "Техника: ${task.assigned.machine}",
                    style = title3MediumStyle
                )

                Text(
                    text = "Агрегат: ${task.assigned.agregate}",
                    style = title3MediumStyle
                )

                Text(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = "Дата: ${task.date}",
                    style = title3MediumStyle
                )
            }
        }
    }
}