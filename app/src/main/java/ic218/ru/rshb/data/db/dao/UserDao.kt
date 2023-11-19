package ic218.ru.rshb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ic218.ru.rshb.domain.entity.Employer


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Employer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user: List<Employer>)

    @Query("SELECT * FROM user_profile")
    suspend fun getUsers(): List<Employer>

    @Query("SELECT * FROM user_profile WHERE id ==:id")
    suspend fun getUserById(id: Int): Employer?

    @Query("SELECT * FROM user_profile WHERE groupid ==:groupId")
    suspend fun getUsersByRole(groupId: Int): List<Employer>

    @Query("DELETE FROM user_profile")
    suspend fun clearUsers()
}