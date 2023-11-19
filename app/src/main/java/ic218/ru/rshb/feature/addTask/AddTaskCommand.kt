package ic218.ru.rshb.feature.addTask

sealed interface AddTaskCommand {
    object Back: AddTaskCommand
    object SyncDb: AddTaskCommand
}