package ic218.ru.rshb.feature.taskDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ic218.ru.rshb.R
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title3MediumStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskDetailScreen(
    navController: NavHostController,
    taskId: Int,
    viewModel: TaskDetailViewModel = koinViewModel()
) {

    TasksScreenMain(navController, viewModel)

    LaunchedEffect(taskId) {
        viewModel.getTaskInfo(taskId)
    }
}

@Composable
private fun TasksScreenMain(
    navController: NavHostController,
    viewModel: TaskDetailViewModel,
) {
    val task = viewModel.tasks ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp, top = 26.dp)
    ) {
        Title(task) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PrimaryInfo(task)
            ParametersInfo(task)
            AdditionalInfo(task)
            Contacts(task)
        }
    }
}

@Composable
fun Title(task: Task, onClick: () -> Unit) {
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
            text = "Задача #${task.id}",
            style = headerBigBoldStyle
        )
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
                .padding(start = 15.dp, top = 14.dp, bottom = 10.dp),
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