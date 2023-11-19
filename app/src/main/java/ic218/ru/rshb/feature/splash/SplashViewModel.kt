package ic218.ru.rshb.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.domain.repositories.PrefsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    private val _navigateToChannel = Channel<SplashScreenNavItem>()
    val navigateToChannel = _navigateToChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(1000)

            if (prefsRepository.getLogged() && prefsRepository.getLoggedUserId() != -1) {
                _navigateToChannel.send(SplashScreenNavItem.Main)
            } else {
                _navigateToChannel.send(SplashScreenNavItem.Auth)
            }
        }
    }
}