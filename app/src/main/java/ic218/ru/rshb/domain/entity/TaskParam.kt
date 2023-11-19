package ic218.ru.rshb.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class TaskParam(
    val id: Int,
    val name: String,
    val shortName: String,
    val value: String = String(),
    val characteristic: String = String()
)