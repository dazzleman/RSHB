package ic218.ru.rshb.data.db.converters

import androidx.room.TypeConverter
import ic218.ru.rshb.domain.entity.TaskField

class TaskFieldTypeDbConverter {

    @TypeConverter
    fun fromTaskFiledTypeParameter(name: String): TaskField.Type {
        return TaskField.Type.valueOf(name)
    }

    @TypeConverter
    fun toTaskFiledType(type: TaskField.Type): String {
        return type.name
    }
}