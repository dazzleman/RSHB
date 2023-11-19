package ic218.ru.rshb.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.entity.Employer
import ic218.ru.rshb.domain.repositories.PrefsRepository
import ic218.ru.rshb.domain.repositories.ProfileRepository
import ic218.ru.rshb.feature.addTask.TaskAddItemType
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(
    private val prefsRepository: PrefsRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var profile by mutableStateOf<Employer?>(null)
        private set

    init {
        viewModelScope.launch {
            val userId = prefsRepository.getLoggedUserId()
            profile = profileRepository.getUserById(userId)
        }
    }

    fun logOff() {
        runBlocking {
            prefsRepository.setLogged(false)
        }
    }
}