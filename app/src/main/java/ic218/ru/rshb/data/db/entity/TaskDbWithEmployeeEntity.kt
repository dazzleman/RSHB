package ic218.ru.rshb.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ic218.ru.rshb.domain.entity.Employer
import ic218.ru.rshb.domain.entity.TaskParam

data class TaskDbWithEmployeeEntity(
    @Embedded
    val task: TaskDbEntity,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "id"
    )
    val owner: Employer,
    @Relation(
        parentColumn = "assignedId",
        entityColumn = "id"
    )
    val assigned: Employer,
)
