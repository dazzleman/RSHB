package ic218.ru.rshb.feature.currentTasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.repositories.TasksRepository
import kotlinx.coroutines.launch

class CurrentTasksViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    var tasks by mutableStateOf<List<Task>>(emptyList())

    init {
        viewModelScope.launch {
            tasksRepository.getWorkTasks()
                .collect {
                    tasks = it
                }
        }
    }

    fun finishTask(taskId: Int) {
        viewModelScope.launch {
            tasksRepository.updateTaskStatus(taskId, Task.Status.FINISH)
        }
    }

    fun pauseTask(taskId: Int) {
        viewModelScope.launch {
            tasksRepository.updateTaskStatus(taskId, Task.Status.PAUSE)
        }
    }

    fun resumeTask(taskId: Int) {
        viewModelScope.launch {
            tasksRepository.updateTaskStatus(taskId, Task.Status.WORK)
        }
    }
}