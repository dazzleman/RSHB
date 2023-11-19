package ic218.ru.rshb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ic218.ru.rshb.domain.entity.TaskField


@Dao
interface TaskFieldDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskFields(fields: List<TaskField>)

    @Query("SELECT * from task_fields ORDER BY sortOrder")
    suspend fun getTaskFields(): List<TaskField>

    @Query("SELECT * from task_fields WHERE type ==:type")
    suspend fun getTaskFieldByType(type: TaskField.Type): List<TaskField>

    @Query("SELECT * from task_fields WHERE operationId LIKE :operationId")
    suspend fun getTaskFieldByOperation(operationId: Int): List<TaskField>

    @Query("DELETE FROM task_fields")
    suspend fun clearTaskFields()
}