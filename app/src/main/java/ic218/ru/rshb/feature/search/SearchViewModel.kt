package ic218.ru.rshb.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.entity.TaskField
import ic218.ru.rshb.domain.repositories.ProfileRepository
import ic218.ru.rshb.domain.repositories.TasksRepository
import ic218.ru.rshb.feature.addTask.TaskAddItemType
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tasksRepository: TasksRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var items by mutableStateOf(emptyList<TaskAddItemType>())
        private set

    private val listType = mutableListOf<TaskAddItemType>()

    fun setType(index: Int) {
        when (TaskField.Type.entries[index]) {
            TaskField.Type.OPERATION -> {
                viewModelScope.launch {
                    tasksRepository.getOperations().forEach {
                        listType.add(
                            TaskAddItemType(
                                type = TaskField.Type.OPERATION,
                                name = it.title,
                                hint = "Выберите...",
                                desc = "Тип задачи",
                                customParam = it.param
                            )
                        )
                    }

                    items = listType.toList()
                }
            }

            TaskField.Type.FIELD -> {
                viewModelScope.launch {
                    tasksRepository.getFields().forEach {
                        listType.add(
                            TaskAddItemType(
                                type = TaskField.Type.FIELD,
                                name = it.title,
                                hint = "Выберите...",
                                desc = "Поле",
                            )
                        )
                    }

                    items = listType.toList()
                }
            }

            TaskField.Type.EMPLOYEE -> {
                viewModelScope.launch {
                    val users = profileRepository.getUsersByRole(1)
                    users.forEach { user ->
                        listType.add(
                            TaskAddItemType(
                                type = TaskField.Type.EMPLOYEE,
                                name = user.username,
                                hint = "Выберите...",
                                desc = "Исполнитель",
                                value = user.id.toString(),
                            )
                        )
                    }

                    items = listType.toList()
                }
            }

            TaskField.Type.CULTURE -> {
                viewModelScope.launch {
                    tasksRepository.getCultures().forEach {
                        listType.add(
                            TaskAddItemType(
                                type = TaskField.Type.CULTURE,
                                name = it.title,
                                hint = "Выберите...",
                                desc = "Культура",
                            )
                        )
                    }

                    items = listType.toList()
                }
            }

            TaskField.Type.PRIORITY -> {
                viewModelScope.launch {
                    tasksRepository.getPriority().forEach {
                        listType.add(
                            TaskAddItemType(
                                type = TaskField.Type.PRIORITY,
                                name = it.title,
                                hint = "Выберите...",
                                desc = "Приоритет",
                                value = it.code.name
                            )
                        )
                    }

                    items = listType.toList()
                }
            }

            TaskField.Type.SOLVENT -> {
                listType.add(
                    TaskAddItemType(
                        type = TaskField.Type.SOLVENT,
                        name = "Азотный",
                        hint = "Выберите...",
                        desc = "Тип раствора",
                    )
                )
                listType.add(
                    TaskAddItemType(
                        type = TaskField.Type.SOLVENT,
                        name = "Амиачный",
                        hint = "Выберите...",
                        desc = "Тип раствора",
                    )
                )
                items = listType.toList()
            }

            else -> Unit
        }
    }

    fun search(text: String) {
        items = listType.filter { it.name.contains(text) }
    }
}