package ic218.ru.rshb.feature.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ic218.ru.rshb.domain.TaskModelTest
import ic218.ru.rshb.domain.entity.Task

class StatisticsViewModel : ViewModel() {

    var items by mutableStateOf(emptyList<Task>())
        private set

    init {
        items = TaskModelTest.tasks
    }
}