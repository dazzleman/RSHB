package ic218.ru.rshb.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cultures")
data class Culture(
    @PrimaryKey val id: Int,
    val title: String,
)
