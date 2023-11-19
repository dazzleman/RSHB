package ic218.ru.rshb.data.db.converters

import androidx.room.TypeConverter
import ic218.ru.rshb.domain.entity.TaskField

class TaskFieldParamDbConverter {

    @TypeConverter
    fun from(name: String): TaskField.CustomParam {
        return TaskField.CustomParam.valueOf(name)
    }

    @TypeConverter
    fun to(type: TaskField.CustomParam): String {
        return type.name
    }
}