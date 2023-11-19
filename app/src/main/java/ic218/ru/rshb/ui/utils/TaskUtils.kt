package ic218.ru.rshb.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ic218.ru.rshb.domain.entity.Task

fun getTaskPriorityTitle(priority: Task.Priority): String {
    return when (priority) {
        Task.Priority.LOW -> "Низкий"
        Task.Priority.NORMAL -> "Обычный"
        Task.Priority.HIGH -> "Высокий"
    }
}

@Composable
fun getTaskPriorityColor(priority: Task.Priority): Color {
    return when (priority) {
        Task.Priority.LOW -> Color(0xFF7A7E79)
        Task.Priority.NORMAL -> Color(0xFF2FFF00)
        Task.Priority.HIGH -> Color(0xFFFF0000)
    }
}

@Composable
fun getTaskStatusTitle(status: Task.Status): String {
    return when (status) {
        Task.Status.TODO -> "Назначена"
        Task.Status.WORK -> "В работе"
        Task.Status.PAUSE -> "Пауза"
        Task.Status.FINISH -> "Завершена"
    }
}