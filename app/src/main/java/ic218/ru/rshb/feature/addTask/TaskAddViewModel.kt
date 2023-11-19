package ic218.ru.rshb.feature.addTask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.entity.Employer
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.entity.TaskField
import ic218.ru.rshb.domain.entity.TaskParam
import ic218.ru.rshb.domain.repositories.PrefsRepository
import ic218.ru.rshb.domain.repositories.ProfileRepository
import ic218.ru.rshb.domain.repositories.TasksRepository
import ic218.ru.rshb.ui.utils.getTaskPriorityTitle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAddViewModel(
    private val tasksRepository: TasksRepository,
    private val prefsRepository: PrefsRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _navigateToChannel = Channel<AddTaskCommand>()
    val navigateToChannel = _navigateToChannel.receiveAsFlow()

    var items by mutableStateOf(emptyList<TaskAddItemType>())
    var enabledCreateTask by mutableStateOf(false)

    private val resultMap = hashMapOf<String, String>()

    private val owner: Employer

    init {
        items = listOf(
            TaskAddItemType(
                type = TaskField.Type.OPERATION,
                name = String(),
                desc = "Тип задачи",
                hint = "Выберите..."
            )
        )

        owner = runBlocking {
            val id = prefsRepository.getLoggedUserId()
            profileRepository.getUserById(id) ?: error("Owner not found")
        }
    }

    fun selectedType(itemType: TaskAddItemType?) {
        val type = itemType ?: return

        when (itemType.type) {
            TaskField.Type.OPERATION -> {
                resultMap.clear()
                enabledCreateTask = false

                viewModelScope.launch {
                    saveResult(type, type.value.ifEmpty { type.name })

                    val operationId = when (itemType.customParam) {
                        TaskField.CustomParam.SOWING -> 1
                        TaskField.CustomParam.TILLAGE -> 2
                        TaskField.CustomParam.PROTECT -> 3
                        else -> error("Operation not exist")
                    }

                    val items = tasksRepository.getTaskFieldsByOperation(operationId).map { taskField ->
                        val name = when (taskField.type) {
                            TaskField.Type.DATE -> Date().let {
                                SimpleDateFormat(
                                    "dd.MM.yy",
                                    Locale.getDefault()
                                ).format(it)
                            }

                            TaskField.Type.PRIORITY -> getTaskPriorityTitle(Task.Priority.NORMAL)
                            else -> String()
                        }

                        val value = when (taskField.type) {
                            TaskField.Type.PRIORITY -> Task.Priority.NORMAL.name
                            else -> String()
                        }

                        TaskAddItemType(
                            type = taskField.type,
                            name = name,
                            desc = taskField.desc,
                            hint = "Выберите...",
                            value = value,
                            isFilled = name.isNotEmpty(),
                            isRequired = taskField.isRequired,
                            shortDesc = taskField.shortDesc,
                            customParam = taskField.customParam
                        )
                    }

                    items.forEach { internalType ->
                        if (internalType.isFilled) saveResult(
                            internalType,
                            internalType.value.ifEmpty { internalType.name })
                    }

                    this@TaskAddViewModel.items = buildList {
                        add(type.copy(isFilled = true))
                        addAll(items)
                    }
                }
            }

            else -> {
                saveResult(type, type.value.ifEmpty { type.name })
                updateItems(type, true)
                enabledCreateTask = isEnabledButton()
            }
        }
    }

    fun changeCustomValue(value: String, type: TaskAddItemType) {
        val isFilled = value.isNotEmpty()

        saveResult(type, value)
        updateItems(type, isFilled)
        enabledCreateTask = isEnabledButton()
    }

    fun startTask() {
        val operation = resultMap[TaskField.Type.OPERATION.name].orEmpty()
        val pole = resultMap[TaskField.Type.FIELD.name].orEmpty()
        val date = resultMap[TaskField.Type.DATE.name].orEmpty()
        val priority =
            resultMap[TaskField.Type.PRIORITY.name]?.let { Task.Priority.valueOf(it) } ?: Task.Priority.NORMAL
        val additionalInfo = resultMap[TaskField.CustomParam.ADDITIONAL.name].orEmpty()
        val assigned = resultMap[TaskField.Type.EMPLOYEE.name]?.let {
            runBlocking {
                profileRepository.getUserById(it.toIntOrNull() ?: 0)
            }
        } ?: error("Employee not found")

        val task = Task(
            id = 0,
            operation = operation,
            status = Task.Status.TODO,
            pole = pole,
            date = date,
            owner = owner,
            assigned = assigned,
            priority = priority,
            additionalInfo = additionalInfo,
            params = getParams()
        )

        viewModelScope.launch {
            tasksRepository.createTask(task)
            _navigateToChannel.send(AddTaskCommand.SyncDb)
            _navigateToChannel.send(AddTaskCommand.Back)
        }
    }

    private fun saveResult(type: TaskAddItemType, value: String) {
        val key = if (type.type == TaskField.Type.CUSTOM) {
            type.customParam.name
        } else {
            type.type.name
        }
        resultMap[key] = value
    }

    private fun getParams(): List<TaskParam> {
        return buildList {
            resultMap[TaskField.CustomParam.DEPTH.name]?.also { result ->
                items.find { it.customParam == TaskField.CustomParam.DEPTH }?.also { itemType ->
                    add(
                        TaskParam(
                            id = 0,
                            name = itemType.desc,
                            shortName = itemType.shortDesc,
                            value = result,
                            characteristic = "см"
                        )
                    )
                }
            }
            resultMap[TaskField.CustomParam.SPEED.name]?.also { result ->
                items.find { it.customParam == TaskField.CustomParam.SPEED }?.also { itemType ->
                    add(
                        TaskParam(
                            id = 0,
                            name = itemType.desc,
                            shortName = itemType.shortDesc,
                            value = result,
                            characteristic = "км/ч"
                        )
                    )
                }
            }
            resultMap[TaskField.CustomParam.SOLVENT.name]?.also { result ->
                items.find { it.customParam == TaskField.CustomParam.SOLVENT }?.also { itemType ->
                    add(
                        TaskParam(
                            id = 0,
                            name = itemType.desc,
                            shortName = itemType.shortDesc,
                            value = result,
                            characteristic = "л/га"
                        )
                    )
                }
            }
        }
    }

    private fun updateItems(type: TaskAddItemType, isFilled: Boolean) {
        items = items.map { item ->
            if (item.type == type.type && item.desc == type.desc) {
                type.copy(isFilled = isFilled)
            } else {
                item
            }
        }
    }

    private fun isEnabledButton() = !items.filter { it.isRequired }.any { !it.isFilled }
}