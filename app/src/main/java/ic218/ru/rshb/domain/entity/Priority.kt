package ic218.ru.rshb.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "priorities")
data class Priority(
    @PrimaryKey val id: Int,
    val title: String,
    val code: Task.Priority
)
