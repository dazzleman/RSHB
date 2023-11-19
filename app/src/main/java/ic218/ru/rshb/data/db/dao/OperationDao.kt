package ic218.ru.rshb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ic218.ru.rshb.domain.entity.Culture
import ic218.ru.rshb.domain.entity.Operation


@Dao
interface OperationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperations(fields: List<Operation>)

    @Query("SELECT * from operations")
    suspend fun getOperations(): List<Operation>

    @Query("DELETE FROM operations")
    suspend fun clearOperations()
}