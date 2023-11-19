package ic218.ru.rshb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ic218.ru.rshb.domain.entity.Field
import ic218.ru.rshb.domain.entity.TaskField


@Dao
interface FieldDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFields(fields: List<Field>)

    @Query("SELECT * from fields")
    suspend fun getFields(): List<Field>

    @Query("DELETE FROM fields")
    suspend fun clearFields()
}