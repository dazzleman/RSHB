package ic218.ru.rshb.feature.currentTasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import ic218.ru.rshb.R
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title3MediumStyle
import ic218.ru.rshb.ui.theme.title3NormalStyle
import ic218.ru.rshb.ui.theme.title4NormalStyle
import ic218.ru.rshb.ui.utils.getTaskStatusTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrentTasksScreen(
    navController: NavHostController,
    viewModel: CurrentTasksViewModel = koinViewModel(),
) {
    val tasks = viewModel.tasks

    Text(
        modifier = Modifier
            .padding(top = 26.dp)
            .fillMaxWidth(),
        text = "Текущая работа",
        style = headerBigBoldStyle,
        textAlign = TextAlign.Center
    )

    if (tasks.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Выберите задачу\nво вкладке “Задачи”",
                style = title3MediumStyle,
                color = Color(0x66FFFFFF),
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            modifier = Modifier
                .padding(top = 76.dp, start = 15.dp, end = 15.dp)
                .fillMaxSize()
        ) {
            tasks.forEach { task ->
                CurrentTaskItem(
                    task = task,
                    viewModel = viewModel,
                    onClickTask = {
                        navController.navigate("task_detail/${it.id}")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrentTaskItem(
    task: Task,
    viewModel: CurrentTasksViewModel,
    onClickTask: (Task) -> Unit
) {
    var showFinishDialog by remember {
        mutableStateOf(false)
    }

    if (showFinishDialog) {
        AlertDialog(
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Вы уверены, что хотите\nзавершить задачу?",
                    style = title3MediumStyle,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Вы больше не сможете\nк ней вернутья",
                    style = title4NormalStyle,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.finishTask(task.id)
                    }
                ) {
                    Text(
                        text = "Завершить",
                        style = title4NormalStyle,
                        color = Color(0xFFE54545)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showFinishDialog = false
                    }
                ) {
                    Text(
                        text = "Отмена",
                        style = title4NormalStyle,
                        color = Color(0xFF4C7EFE)
                    )
                }
            },

            onDismissRequest = {
                showFinishDialog = false
            })
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF212E3D), shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .padding(bottom = 10.dp)
            .clickable {
                onClickTask.invoke(task)
            }
    ) {

        val (header1, title1Key0, title1Key1, button1Key1, button1Key2, button1Key3) = createRefs()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0x40D9D9D9))
                .padding(vertical = 5.dp, horizontal = 15.dp)
                .constrainAs(header1) {
                    top.linkTo(parent.top)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Задача #${task.id}",
                style = title3NormalStyle
            )
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = getTaskStatusTitle(task.status),
                style = title3MediumStyle,
                color = Color(0xFF8AE159)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Поле ${task.pole}",
                style = title3MediumStyle,
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 14.dp)
                .constrainAs(title1Key0) {
                    top.linkTo(header1.bottom)
                },
            text = "Тип задачи: ${task.operation}",
            style = title3MediumStyle
        )

        Column(
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp)
                .constrainAs(title1Key1) {
                    top.linkTo(title1Key0.bottom)
                },
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            task.params.forEach {
                Text(
                    text = "${it.shortName.ifEmpty { it.name }}: ${it.value} ${it.characteristic}",
                    style = title3MediumStyle
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp)
                .size(176.dp, 47.dp)
                .constrainAs(button1Key1) {
                    top.linkTo(title1Key1.bottom)
                    start.linkTo(title1Key1.start)
                    end.linkTo(button1Key2.start)
                },
            shape = RoundedCornerShape(size = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA62626)
            ),
            onClick = {

            }) {
            Text(
                text = "Проблема",
                style = title3MediumStyle,
            )
        }

        Button(
            modifier = Modifier
                .padding(start = 10.dp, end = 15.dp)
                .height(47.dp)
                .constrainAs(button1Key2) {
                    bottom.linkTo(button1Key1.bottom)
                    start.linkTo(button1Key1.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(size = 10.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9C6F2C)
            ),
            onClick = {
                showFinishDialog = true
            }) {
            Text(
                text = "Завершить",
                style = title3MediumStyle,
            )
        }

        Button(
            modifier = Modifier
                .padding(bottom = 10.dp, start = 10.dp, end = 15.dp)
                .height(47.dp)
                .constrainAs(button1Key3) {
                    bottom.linkTo(button1Key2.top)
                    start.linkTo(button1Key2.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(size = 10.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF737373)
            ),
            onClick = {
                when (task.status) {
                    Task.Status.WORK -> viewModel.pauseTask(task.id)
                    Task.Status.PAUSE -> viewModel.resumeTask(task.id)
                    else -> Unit
                }
            }) {
            Text(
                text = when (task.status) {
                    Task.Status.WORK -> "Пауза"
                    Task.Status.PAUSE -> "Старт"
                    else -> String()
                },
                style = title3MediumStyle,
            )
        }


        val (header2, image2Key1, image2Key2, image2Key3, title2Key1, title2Key2, title2Key3, title2Key4) = createRefs()

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .background(color = Color(0x40D9D9D9))
                .padding(vertical = 5.dp)
                .constrainAs(header2) {
                    top.linkTo(button1Key1.bottom)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = "Текущая техника",
                style = title3NormalStyle
            )
        }

        Image(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .size(141.dp, 128.dp)
                .constrainAs(image2Key1) {
                    top.linkTo(header2.bottom)
                    start.linkTo(parent.start)
                },
            painter = painterResource(id = R.drawable.image_traktor),
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .padding(start = 10.dp, top = 12.dp)
                .size(141.dp, 128.dp)
                .constrainAs(image2Key2) {
                    top.linkTo(image2Key1.bottom)
                    start.linkTo(parent.start)
                },
            painter = painterResource(id = R.drawable.image_agregat),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .padding(start = 12.dp, end = 15.dp, top = 10.dp)
                .constrainAs(title2Key1) {
                    top.linkTo(image2Key1.top)
                    start.linkTo(image2Key1.end)
                },
            text = "Машина",
            style = title3MediumStyle
        )

        Image(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp)
                .size(140.dp, 38.dp)
                .constrainAs(image2Key3) {
                    top.linkTo(title2Key1.bottom)
                    start.linkTo(image2Key1.end)
                },
            painter = painterResource(id = R.drawable.image_gos_znak),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text(
            modifier = Modifier
                .padding(start = 12.dp, end = 15.dp, top = 8.dp)
                .constrainAs(title2Key2) {
                    top.linkTo(image2Key3.bottom)
                    start.linkTo(image2Key1.end)
                },
            text = task.assigned.machine.toString(),// "К-742 #1254",
            style = title4NormalStyle
        )

        Text(
            modifier = Modifier
                .padding(start = 12.dp, end = 15.dp, top = 12.dp)
                .constrainAs(title2Key3) {
                    top.linkTo(image2Key2.top)
                    start.linkTo(image2Key2.end)
                },
            text = "Агрегат",
            style = title3MediumStyle
        )

        Text(
            modifier = Modifier
                .padding(start = 12.dp, end = 15.dp, top = 5.dp)
                .constrainAs(title2Key4) {
                    top.linkTo(title2Key3.bottom)
                    start.linkTo(image2Key2.end)
                },
            text = task.assigned.agregate.orEmpty(), //"Horsh Pronto NT12 #1",
            style = title4NormalStyle
        )
    }
}

