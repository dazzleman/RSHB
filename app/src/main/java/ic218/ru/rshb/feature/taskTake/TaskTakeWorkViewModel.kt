package ic218.ru.rshb.feature.taskTake

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class TaskTakeWorkViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    var tasks by mutableStateOf<Task?>(null)
    var takeButtonExist by mutableStateOf(false)

    fun getTaskInfo(taskId: Int) {
        viewModelScope.launch {
            val task = tasksRepository.getTask(taskId)
            tasks = task

            takeButtonExist = task.status == Task.Status.TODO &&
                tasksRepository.getTasksByStatuses(Task.Status.WORK, Task.Status.PAUSE).firstOrNull()?.isEmpty() ?: true
        }
    }

    fun takeTaskToWork(taskId: Int) {
        viewModelScope.launch {
            tasksRepository.updateTaskStatus(taskId, Task.Status.WORK)
        }
    }
}