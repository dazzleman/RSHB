package ic218.ru.rshb.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import ic218.ru.rshb.data.db.MyRoomDb
import ic218.ru.rshb.data.repositories.AuthRepositoryImpl
import ic218.ru.rshb.data.repositories.PrefsRepositoryImpl
import ic218.ru.rshb.data.repositories.ProfileRepositoryImpl
import ic218.ru.rshb.data.repositories.TasksRepositoryImpl
import ic218.ru.rshb.domain.repositories.AuthRepository
import ic218.ru.rshb.domain.repositories.PrefsRepository
import ic218.ru.rshb.domain.repositories.ProfileRepository
import ic218.ru.rshb.domain.repositories.TasksRepository
import ic218.ru.rshb.utils.NfcManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val appModule = module {

    single { androidApplication().dataStore }
    single { MyRoomDb.getDatabase(androidApplication()) }

    single { get<MyRoomDb>().taskDao() }
    single { get<MyRoomDb>().userDao() }
    single { get<MyRoomDb>().taskFieldDao() }
    single { get<MyRoomDb>().fieldDao() }
    single { get<MyRoomDb>().cultureDao() }
    single { get<MyRoomDb>().priorityDao() }
    single { get<MyRoomDb>().operationDao() }

    single { NfcManager() }

    single<TasksRepository> { TasksRepositoryImpl(get(), get(), get(), get(), get(), get(), get()) }
    single<PrefsRepository> { PrefsRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
}