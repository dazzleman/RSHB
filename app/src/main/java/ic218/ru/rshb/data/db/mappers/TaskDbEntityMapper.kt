package ic218.ru.rshb.data.db.mappers

import ic218.ru.rshb.data.db.entity.TaskDbWithEmployeeEntity
import ic218.ru.rshb.domain.entity.Task

class TaskDbEntityMapper {

    fun map(items: List<TaskDbWithEmployeeEntity>): List<Task> {
        return items.map {
            Task(
                id = it.task.id,
                operation = it.task.operation,
                status = Task.Status.entries[it.task.status],
                priority = Task.Priority.entries[it.task.priority],
                pole = it.task.pole,
                date = it.task.date,
                additionalInfo = it.task.additionalInfo,
                params = it.task.params,
                owner = it.owner,
                assigned = it.assigned
            )
        }
    }

    fun map(item: TaskDbWithEmployeeEntity): Task {
        return Task(
            id = item.task.id,
            operation = item.task.operation,
            status = Task.Status.entries[item.task.status],
            priority = Task.Priority.entries[item.task.priority],
            pole = item.task.pole,
            date = item.task.date,
            additionalInfo = item.task.additionalInfo,
            params = item.task.params,
            owner = item.owner,
            assigned = item.assigned
        )
    }
}