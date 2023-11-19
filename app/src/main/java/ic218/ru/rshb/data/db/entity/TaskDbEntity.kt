package ic218.ru.rshb.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ic218.ru.rshb.domain.entity.TaskParam

@Entity(tableName = "tasks")
data class TaskDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ownerId: Int,
    val assignedId: Int,
    val operation: String,
    val status: Int,
    val pole: String,
    val date: String,
    val priority: Int,
    val additionalInfo: String,
    val params: List<TaskParam>
)
