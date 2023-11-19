package ic218.ru.rshb.domain.entity

data class Task(
    val id: Int,
    val operation: String,
    val status: Status,
    val pole: String,
    val date: String,
    val owner: Employer,
    val assigned: Employer,
    val priority: Priority,
    val additionalInfo: String,
    val params: List<TaskParam>
) {

    enum class Status {
        TODO, WORK, PAUSE, FINISH
    }

    enum class Priority {
        LOW, NORMAL, HIGH
    }
}