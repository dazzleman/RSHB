package ic218.ru.rshb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ic218.ru.rshb.data.db.entity.TaskDbEntity
import ic218.ru.rshb.data.db.entity.TaskDbWithEmployeeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskDbEntity)

    @Query("SELECT * FROM tasks")
    @Transaction
    fun getTasks(): Flow<List<TaskDbWithEmployeeEntity>>

    @Query("SELECT * FROM tasks WHERE id==:id")
    @Transaction
    suspend fun getTask(id: Int): TaskDbWithEmployeeEntity

    @Query("SELECT * FROM tasks WHERE status IN(:statuses)")
    @Transaction
    fun getTasksByStatus(vararg statuses: Int): Flow<List<TaskDbWithEmployeeEntity>>

    @Query("SELECT * FROM tasks WHERE status == 1 OR status == 2 ")
    @Transaction
    fun getWorkTasks(): Flow<List<TaskDbWithEmployeeEntity>>

    @Query("SELECT * FROM tasks WHERE status == 0")
    @Transaction
    fun getTodoTasks(): Flow<List<TaskDbWithEmployeeEntity>>

    @Query("SELECT * FROM tasks WHERE status == 3")
    @Transaction
    fun getFinishedTasks(): Flow<List<TaskDbWithEmployeeEntity>>

    @Query("UPDATE tasks SET status = :status WHERE id == :taskId")
    fun updateTaskStatus(taskId: Int, status: Int)

    @Query("DELETE FROM tasks")
    suspend fun clearTasks()
}