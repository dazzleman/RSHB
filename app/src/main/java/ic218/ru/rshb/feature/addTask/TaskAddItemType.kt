package ic218.ru.rshb.feature.addTask

import android.os.Parcelable
import ic218.ru.rshb.domain.entity.TaskField
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskAddItemType(
    val name: String,
    val type: TaskField.Type,
    val hint: String,
    val desc: String = String(),
    val shortDesc: String = String(),
    val subDesc: String = String(),
    val isFilled: Boolean = false,
    val isRequired: Boolean = true,
    val value: String = String(),
    val customParam: TaskField.CustomParam = TaskField.CustomParam.NONE
) : Parcelable