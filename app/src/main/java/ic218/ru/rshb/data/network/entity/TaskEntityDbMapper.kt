package ic218.ru.rshb.data.network.entity

import ic218.ru.rshb.data.db.entity.TaskDbEntity
import ic218.ru.rshb.domain.entity.Task

class TaskEntityDbMapper {

    fun map(item: Task): TaskDbEntity {
        return TaskDbEntity(
            id = item.id,
            ownerId = item.owner.id,
            assignedId = item.assigned.id,
            operation = item.operation,
            pole = item.pole,
            date = item.date,
            status = item.status.ordinal,
            priority = item.priority.ordinal,
            additionalInfo = item.additionalInfo,
            params = item.params
        )
    }
}