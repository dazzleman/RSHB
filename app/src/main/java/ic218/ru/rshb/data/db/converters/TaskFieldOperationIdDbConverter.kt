package ic218.ru.rshb.data.db.converters

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class TaskFieldOperationIdDbConverter {

    @TypeConverter
    fun from(param: String): List<Int> {
        return Json.decodeFromString(ListSerializer(Int.serializer()), param)
    }

    @TypeConverter
    fun to(ids: List<Int>): String {
        return Json.encodeToString(ListSerializer(Int.serializer()), ids)
    }
}