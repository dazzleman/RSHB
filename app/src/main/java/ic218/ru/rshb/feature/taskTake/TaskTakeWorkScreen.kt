package ic218.ru.rshb.feature.taskTake

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.ui.theme.title3MediumStyle
import ic218.ru.rshb.ui.theme.title4NormalStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskTakeWorkScreen(
    taskId: Int,
    onClick: () -> Unit,
    viewModel: TaskTakeWorkViewModel = koinViewModel()
) {
    val task = viewModel.tasks

    if (task != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            PrimaryInfo(task)
            ParametersInfo(task)
            AdditionalInfo(task)
            Contacts(task)
            SelectedTechnics(task)
            SelectedAggregate(task)
            TakeJob(viewModel) {
                onClick.invoke()
                viewModel.takeTaskToWork(taskId)
            }
        }
    }

    LaunchedEffect(taskId) {
        viewModel.getTaskInfo(taskId)
    }
}

@Composable
private fun PrimaryInfo(task: Task) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF263546), shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color(0x26D9D9D9)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = "Основная информация",
                style = title3MediumStyle,
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 14.dp),
            text = "Тип задачи: ${task.operation}",
            style = title3MediumStyle,
        )

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp),
            text = "Поле:  ${task.pole}",
            style = title3MediumStyle,
        )

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp),
            text = "Исполнитель: ${task.assigned.username}",
            style = title3MediumStyle,
        )

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp),
            text = "Назначил:  ${task.owner.username}",
            style = title3MediumStyle,
        )

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp, bottom = 15.dp),
            text = "Дата: ${task.date}",
            style = title3MediumStyle,
        )
    }
}

@Composable
private fun ParametersInfo(task: Task) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF263546), shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color(0x26D9D9D9)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = "Параметры",
                style = title3MediumStyle,
            )
        }

        Column(
            modifier = Modifier
                .padding(vertical = 15.dp, horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            task.params.forEach { param ->
                Text(
                    text = "${param.shortName.ifEmpty { param.name }}: ${param.value} ${param.characteristic}",
                    style = title3MediumStyle,
                )
            }
        }
    }
}

@Composable
private fun AdditionalInfo(task: Task) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .height(135.dp)
            .background(color = Color(0xFF263546), shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color(0x26D9D9D9)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = "Дополнительная информация",
                style = title3MediumStyle,
            )
        }

        if (task.additionalInfo.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 30.dp),
                    text = "Дополнительная информация отсутствует",
                    style = title3MediumStyle,
                    textAlign = TextAlign.Center,
                    color = Color(0x80FFFFFF)
                )
            }
        } else {
            Text(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 15.dp)
                    .fillMaxSize(),
                text = task.additionalInfo,
                style = title3MediumStyle,
            )
        }
    }
}

@Composable
private fun Contacts(task: Task) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF263546), shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color(0x26D9D9D9)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = "Контакты",
                style = title3MediumStyle,
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 14.dp),
            text = task.owner.jobtitle,
            style = title3MediumStyle,
            textAlign = TextAlign.Center,
            color = Color(0x80FFFFFF)
        )
        Text(
            modifier = Modifier
                .padding(start = 15.dp),
            text = task.owner.username,
            style = title3MediumStyle,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 14.dp, bottom = 15.dp),
            text = "Телефон: ${task.owner.phone}",
            style = title3MediumStyle,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SelectedTechnics(task: Task) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = "Техника",
            style = title4NormalStyle
        )

        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .height(50.dp)
                .fillMaxWidth()
                .background(color = Color(0xFF263546), shape = RoundedCornerShape(size = 5.dp))
                .padding(start = 15.dp, end = 15.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = task.assigned.machine.toString(),
                style = title4NormalStyle,
            )
        }
    }
}

@Composable
private fun SelectedAggregate(task: Task) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = "Агрегат",
            style = title4NormalStyle
        )

        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .height(50.dp)
                .fillMaxWidth()
                .background(color = Color(0xFF263546), shape = RoundedCornerShape(size = 5.dp))
                .padding(start = 15.dp, end = 15.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = task.assigned.agregate.orEmpty(),
                style = title4NormalStyle,
            )
        }
    }
}

@Composable
private fun TakeJob(
    viewModel: TaskTakeWorkViewModel,
    onClick: () -> Unit
) {

    val isVisible = viewModel.takeButtonExist

    if (isVisible) {
        Button(
            modifier = Modifier
                .padding(top = 34.dp, bottom = 30.dp)
                .fillMaxWidth()
                .height(47.dp),
            shape = RoundedCornerShape(size = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9C6F2C)
            ),
            onClick = onClick
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = "Взять в работу",
                style = title3MediumStyle,
            )
        }
    } else {
        Spacer(modifier = Modifier.height(32.dp))
    }
}