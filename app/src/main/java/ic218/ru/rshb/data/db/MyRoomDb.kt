package ic218.ru.rshb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ic218.ru.rshb.data.db.converters.TaskFieldOperationIdDbConverter
import ic218.ru.rshb.data.db.converters.TaskFieldParamDbConverter
import ic218.ru.rshb.data.db.converters.TaskFieldTypeDbConverter
import ic218.ru.rshb.data.db.converters.TaskParamsDbConverter
import ic218.ru.rshb.data.db.dao.CultureDao
import ic218.ru.rshb.data.db.dao.FieldDao
import ic218.ru.rshb.data.db.dao.OperationDao
import ic218.ru.rshb.data.db.dao.PriorityDao
import ic218.ru.rshb.data.db.dao.TaskDao
import ic218.ru.rshb.data.db.dao.TaskFieldDao
import ic218.ru.rshb.data.db.dao.UserDao
import ic218.ru.rshb.data.db.entity.TaskDbEntity
import ic218.ru.rshb.domain.TaskModelTest.cultures
import ic218.ru.rshb.domain.TaskModelTest.fields
import ic218.ru.rshb.domain.TaskModelTest.operations
import ic218.ru.rshb.domain.TaskModelTest.priorities
import ic218.ru.rshb.domain.TaskModelTest.taskFields
import ic218.ru.rshb.domain.entity.Culture
import ic218.ru.rshb.domain.entity.Employer
import ic218.ru.rshb.domain.entity.Field
import ic218.ru.rshb.domain.entity.Operation
import ic218.ru.rshb.domain.entity.Priority
import ic218.ru.rshb.domain.entity.TaskField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(
    entities = [
        TaskDbEntity::class,
        Employer::class,
        TaskField::class,
        Field::class,
        Culture::class,
        Priority::class,
        Operation::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(
    TaskParamsDbConverter::class,
    TaskFieldTypeDbConverter::class,
    TaskFieldOperationIdDbConverter::class,
    TaskFieldParamDbConverter::class
)
abstract class MyRoomDb : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
    abstract fun taskFieldDao(): TaskFieldDao
    abstract fun fieldDao(): FieldDao
    abstract fun cultureDao(): CultureDao
    abstract fun priorityDao(): PriorityDao
    abstract fun operationDao(): OperationDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDb? = null

        fun getDatabase(context: Context): MyRoomDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDb::class.java,
                    "my_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            INSTANCE?.taskFieldDao()?.apply {
                                CoroutineScope(Job()).launch {
                                    insertTaskFields(taskFields)
                                }
                            }
                            INSTANCE?.fieldDao()?.apply {
                                CoroutineScope(Job()).launch {
                                    insertFields(fields)
                                }
                            }
                            INSTANCE?.cultureDao()?.apply {
                                CoroutineScope(Job()).launch {
                                    insertCultures(cultures)
                                }
                            }
                            INSTANCE?.operationDao()?.apply {
                                CoroutineScope(Job()).launch {
                                    insertOperations(operations)
                                }
                            }
                            INSTANCE?.priorityDao()?.apply {
                                CoroutineScope(Job()).launch {
                                    insertPriorities(priorities)
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}