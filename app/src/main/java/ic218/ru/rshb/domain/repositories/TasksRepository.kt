package ic218.ru.rshb.domain.repositories

import ic218.ru.rshb.domain.entity.Culture
import ic218.ru.rshb.domain.entity.Field
import ic218.ru.rshb.domain.entity.Operation
import ic218.ru.rshb.domain.entity.Priority
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.entity.TaskField
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun insertTask(task: Task)

    fun getTodoTasks(): Flow<List<Task>>

    fun getWorkTasks(): Flow<List<Task>>

    fun getFinishedTasks(): Flow<List<Task>>

    suspend fun getTask(taskId: Int): Task

    suspend fun createTask(task: Task)

    suspend fun updateTaskStatus(taskId: Int, status: Task.Status)

    fun getTasksByStatuses(vararg statuses: Task.Status): Flow<List<Task>>

    fun syncTaskFields(): Flow<Boolean>

    suspend fun getTaskFieldsByType(type: TaskField.Type): List<TaskField>

    suspend fun getTaskFieldsByOperation(operationId: Int): List<TaskField>

    suspend fun getOperations(): List<Operation>

    suspend fun getFields(): List<Field>

    suspend fun getPriority(): List<Priority>

    suspend fun getCultures(): List<Culture>

}