package ic218.ru.rshb.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "fields")
data class Field(
    @PrimaryKey val id: Int,
    val title: String,
    val lat: Double,
    val lon: Double,
)
