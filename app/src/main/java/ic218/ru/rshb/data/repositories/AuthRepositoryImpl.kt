package ic218.ru.rshb.data.repositories

import ic218.ru.rshb.data.db.dao.UserDao
import ic218.ru.rshb.data.network.ApiResult
import ic218.ru.rshb.data.network.api.AuthNetworkApi
import ic218.ru.rshb.domain.entity.Employer
import ic218.ru.rshb.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class AuthRepositoryImpl(
    private val authNetworkApi: AuthNetworkApi,
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun login(nfcid: String): Flow<ApiResult<Employer>> {
        return authNetworkApi.login(nfcid)
            .onEach {
                if (it is ApiResult.Success) {
                    userDao.insertUser(it.data)
                }
            }
    }
}