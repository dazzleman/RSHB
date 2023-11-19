package ic218.ru.rshb.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "task_fields")
class TaskField(
    @PrimaryKey val id: Int,
    val operationId: List<Int>,
    val sortOrder: Int,
    val type: Type,
    val desc: String,
    val shortDesc: String,
    val isRequired: Boolean,
    val customParam: CustomParam
) {

    enum class Type {
        OPERATION, PRIORITY, DATE, FIELD, EMPLOYEE, CULTURE, SOLVENT, CUSTOM
    }

    enum class CustomParam {
        NONE, SOWING, TILLAGE, PROTECT, DEPTH, SPEED, SOLVENT, ADDITIONAL
    }
}