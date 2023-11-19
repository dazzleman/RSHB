package ic218.ru.rshb.domain.repositories

import ic218.ru.rshb.data.network.ApiResult
import ic218.ru.rshb.domain.entity.Employer
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(nfcid: String): Flow<ApiResult<Employer>>
}