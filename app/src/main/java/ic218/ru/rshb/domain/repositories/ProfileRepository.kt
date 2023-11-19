package ic218.ru.rshb.domain.repositories

import ic218.ru.rshb.domain.entity.Employer
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun syncUsers(): Flow<Boolean>

    suspend fun getUserById(id: Int): Employer?

    suspend fun getUsersByRole(roleId: Int): List<Employer>
}