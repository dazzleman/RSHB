package ic218.ru.rshb.feature.taskDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.repositories.TasksRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    var tasks by mutableStateOf<Task?>(null)

    fun getTaskInfo(taskId: Int) {
        viewModelScope.launch {
            val task = tasksRepository.getTask(taskId)
            tasks = task
        }
    }
}