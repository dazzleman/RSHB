package ic218.ru.rshb.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "operations")
data class Operation(
    @PrimaryKey val id: Int,
    val title: String,
    val param: TaskField.CustomParam
)
