package ic218.ru.rshb.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user_profile")
data class Employer(
    @PrimaryKey val id: Int,
    val nfcid: String,
    val username: String,
    val usersurname: String,
    val groupid: Int,
    val jobtitle: String,
    val phone: String,
    val avatar: String,
    val machine: Int,
    val agregate: String?
    /*    val machine: String,
        val agregate: String*/
)
