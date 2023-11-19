package ic218.ru.rshb.data.repositories

import ic218.ru.rshb.data.db.dao.CultureDao
import ic218.ru.rshb.data.db.dao.FieldDao
import ic218.ru.rshb.data.db.dao.OperationDao
import ic218.ru.rshb.data.db.dao.PriorityDao
import ic218.ru.rshb.data.db.dao.TaskDao
import ic218.ru.rshb.data.db.dao.TaskFieldDao
import ic218.ru.rshb.data.db.mappers.TaskDbEntityMapper
import ic218.ru.rshb.data.network.ApiResult
import ic218.ru.rshb.data.network.api.TaskNetworkApi
import ic218.ru.rshb.data.network.entity.TaskEntityDbMapper
import ic218.ru.rshb.domain.entity.Culture
import ic218.ru.rshb.domain.entity.Field
import ic218.ru.rshb.domain.entity.Operation
import ic218.ru.rshb.domain.entity.Priority
import ic218.ru.rshb.domain.entity.Task
import ic218.ru.rshb.domain.entity.TaskField
import ic218.ru.rshb.domain.repositories.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TasksRepositoryImpl(
    private val taskNetworkApi: TaskNetworkApi,
    private val taskDao: TaskDao,
    private val taskFieldDao: TaskFieldDao,
    private val priorityDao: PriorityDao,
    private val fieldDao: FieldDao,
    private val operationDao: OperationDao,
    private val cultureDao: CultureDao
) : TasksRepository {

    override suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(TaskEntityDbMapper().map(task))
        }
    }

    override suspend fun getTask(taskId: Int): Task {
        return withContext(Dispatchers.IO) {
            taskDao.getTask(taskId).let { TaskDbEntityMapper().map(it) }
        }
    }

    override fun getTodoTasks(): Flow<List<Task>> {
        return taskDao.getTodoTasks()
            .map {
                TaskDbEntityMapper().map(it)
            }
    }

    override fun getWorkTasks(): Flow<List<Task>> {
        return taskDao.getWorkTasks()
            .map {
                TaskDbEntityMapper().map(it)
            }
    }

    override fun getFinishedTasks(): Flow<List<Task>> {
        return taskDao.getFinishedTasks()
            .map {
                TaskDbEntityMapper().map(it)
            }
    }

    override suspend fun createTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(TaskEntityDbMapper().map(task))
        }
    }

    override suspend fun updateTaskStatus(taskId: Int, status: Task.Status) {
        withContext(Dispatchers.IO) {
            taskDao.updateTaskStatus(taskId, status.ordinal)
        }
    }

    override fun getTasksByStatuses(vararg statuses: Task.Status): Flow<List<Task>> {
        return taskDao.getTasksByStatus(*statuses.map { it.ordinal }.toIntArray())
            .map { TaskDbEntityMapper().map(it) }
    }

    override fun syncTaskFields(): Flow<Boolean> {
        return taskNetworkApi.getTaskFields()
            .map {
                if (it is ApiResult.Success) {
                    taskFieldDao.insertTaskFields(it.data)
                    true
                } else {
                    false
                }
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getTaskFieldsByType(type: TaskField.Type): List<TaskField> {
        return withContext(Dispatchers.IO) {
            taskFieldDao.getTaskFieldByType(type)
        }
    }

    override suspend fun getTaskFieldsByOperation(operationId: Int): List<TaskField> {
        return withContext(Dispatchers.IO) {
            taskFieldDao.getTaskFields().filter { it.operationId.contains(operationId) }
        }
    }

    override suspend fun getOperations(): List<Operation> {
        return withContext(Dispatchers.IO) {
            operationDao.getOperations()
        }
    }

    override suspend fun getFields(): List<Field> {
        return withContext(Dispatchers.IO) {
            fieldDao.getFields()
        }
    }

    override suspend fun getPriority(): List<Priority> {
        return withContext(Dispatchers.IO) {
            priorityDao.getPriorities()
        }
    }

    override suspend fun getCultures(): List<Culture> {
        return withContext(Dispatchers.IO) {
            cultureDao.getCultures()
        }
    }
}