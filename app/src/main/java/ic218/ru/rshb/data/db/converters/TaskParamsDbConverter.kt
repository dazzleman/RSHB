package ic218.ru.rshb.data.db.converters

import androidx.room.TypeConverter
import ic218.ru.rshb.domain.entity.TaskParam
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class TaskParamsDbConverter {

    @TypeConverter
    fun fromTaskParameter(value: String): List<TaskParam> {
        return Json.decodeFromString(ListSerializer(TaskParam.serializer()), value)
    }

    @TypeConverter
    fun dateToTimestamp(param: List<TaskParam>): String {
        return Json.encodeToString(ListSerializer(TaskParam.serializer()), param)
    }

}