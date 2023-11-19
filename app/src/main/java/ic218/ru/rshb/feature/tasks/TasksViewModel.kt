package ic218.ru.rshb.feature.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.repositories.PrefsRepository
import ic218.ru.rshb.domain.repositories.ProfileRepository
import ic218.ru.rshb.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tasksRepository: TasksRepository,
    private val prefsRepository: PrefsRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow<TasksScreenState>(TasksScreenState.Loading)
    val uiState: StateFlow<TasksScreenState> = _state.asStateFlow()

    private var internalOpenTasks = listOf<Task>()
    private var internalFinishTasks = listOf<Task>()
    private var selectedButton = TopButtonSelected.FIRST

    var tasks by mutableStateOf<List<Task>>(emptyList())
    var addTasksVisible by mutableStateOf(false)

    init {
        _state.update {
            TasksScreenState.Main
        }

        viewModelScope.launch {
            tasksRepository.getTodoTasks()
                .collect {
                    internalOpenTasks = it
                    if (selectedButton == TopButtonSelected.FIRST) tasks = internalOpenTasks
                }
        }

        viewModelScope.launch {
            tasksRepository.getFinishedTasks()
                .collect {
                    internalFinishTasks = it
                    if (selectedButton == TopButtonSelected.SECOND) tasks = internalFinishTasks
                }
        }

        viewModelScope.launch {
            val userId = prefsRepository.getLoggedUserId()
            val isAgronom = profileRepository.getUserById(userId)?.groupid?.let { it == 1 } ?: false
            addTasksVisible = isAgronom
        }
    }

    fun selectOpenClosedTaskButton(item: TopButtonSelected) {
        selectedButton = item

        when (item) {
            TopButtonSelected.FIRST -> {
                tasks = internalOpenTasks
            }

            TopButtonSelected.SECOND -> {
                tasks = internalFinishTasks
            }
        }
    }
}