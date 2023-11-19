package ic218.ru.rshb.domain.repositories

interface PrefsRepository {

    suspend fun setLogged(isLogged: Boolean)

    suspend fun getLogged(): Boolean

    suspend fun setLoggedUserId(id: Int)

    suspend fun getLoggedUserId(): Int
}