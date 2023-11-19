package ic218.ru.rshb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ic218.ru.rshb.domain.entity.Priority


@Dao
interface PriorityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriorities(fields: List<Priority>)

    @Query("SELECT * from priorities")
    suspend fun getPriorities(): List<Priority>

    @Query("DELETE FROM priorities")
    suspend fun clearPriorities()
}