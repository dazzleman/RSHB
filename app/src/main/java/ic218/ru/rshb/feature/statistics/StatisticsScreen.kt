package ic218.ru.rshb.feature.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title3BoldStyle
import ic218.ru.rshb.ui.theme.title3MediumStyle
import ic218.ru.rshb.ui.theme.title3NormalStyle
import ic218.ru.rshb.ui.utils.getTaskPriorityColor
import ic218.ru.rshb.ui.utils.getTaskStatusTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .padding(top = 26.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize()
    ) {
        Title()
        Info()
        Main(viewModel)
    }
}

@Composable
fun Title() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Статистика",
            style = headerBigBoldStyle
        )
    }
}

@Composable
fun Info() {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
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
                text = "Текущий месяц",
                style = title3MediumStyle,
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 15.dp, top = 14.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = "Выполнено задач: 3",
                style = title3MediumStyle,
            )

            Text(
                text = "К выплате:  74 567 рублей",
                style = title3MediumStyle,
            )
        }
    }
}

@Composable
fun Main(viewModel: StatisticsViewModel) {

    val tasks = viewModel.items

    LazyColumn(
        modifier = Modifier
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tasks) { task ->
            TaskItemList(task)
        }
    }
}

@Composable
fun TaskItemList(
    task: Task,
) {
    Row(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .background(color = Color(0x3359779C), shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight()
                .background(getTaskPriorityColor(task.priority))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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

            Box(
                modifier = Modifier
                    .size(120.dp, 41.dp)
                    .align(Alignment.BottomEnd)
                    .background(color = Color(0x33FFFFFF), shape = RoundedCornerShape(topStart = 10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "29 756 ₽",
                    style = title3NormalStyle
                )
            }
        }
    }
}