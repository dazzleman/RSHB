package ic218.ru.rshb.data.repositories

import ic218.ru.rshb.data.db.dao.UserDao
import ic218.ru.rshb.data.network.ApiResult
import ic218.ru.rshb.data.network.api.ProfileNetworkApi
import ic218.ru.rshb.domain.entity.Employer
import ic218.ru.rshb.domain.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val profileNetworkApi: ProfileNetworkApi,
    private val userDao: UserDao
) : ProfileRepository {

    override suspend fun syncUsers(): Flow<Boolean> {
        return profileNetworkApi.getUsers()
            .map {
                if (it is ApiResult.Success) {
                    userDao.insertUsers(it.data)
                    true
                } else {
                    false
                }
            }
            .catch { emit(false) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getUserById(id: Int): Employer? {
        return userDao.getUserById(id)
    }

    override suspend fun getUsersByRole(groupId: Int): List<Employer> {
        return withContext(Dispatchers.IO) {
            userDao.getUsersByRole(groupId)
        }
    }
}