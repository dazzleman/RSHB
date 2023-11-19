package ic218.ru.rshb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ic218.ru.rshb.domain.entity.Culture


@Dao
interface CultureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCultures(fields: List<Culture>)

    @Query("SELECT * from cultures")
    suspend fun getCultures(): List<Culture>

    @Query("DELETE FROM cultures")
    suspend fun clearCultures()
}