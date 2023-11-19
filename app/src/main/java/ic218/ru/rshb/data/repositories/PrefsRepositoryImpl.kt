package ic218.ru.rshb.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import ic218.ru.rshb.domain.repositories.PrefsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class PrefsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PrefsRepository {


    override suspend fun setLogged(isLogged: Boolean) {
        dataStore.edit { settings ->
            settings[IS_LOGGED] = isLogged
        }
    }

    override suspend fun getLogged(): Boolean {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[IS_LOGGED] ?: false
            }.first()
        }
    }

    override suspend fun setLoggedUserId(id: Int) {
        dataStore.edit { settings ->
            settings[USER_ID] = id
        }
    }

    override suspend fun getLoggedUserId(): Int {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[USER_ID] ?: -1
            }.first()
        }
    }

    private companion object {
        val IS_LOGGED = booleanPreferencesKey("is_logged")
        val USER_ID = intPreferencesKey("user_id")
    }
}