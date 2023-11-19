package ic218.ru.rshb.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ic218.ru.rshb.data.network.ApiResult
import ic218.ru.rshb.domain.repositories.AuthRepository
import ic218.ru.rshb.domain.repositories.PrefsRepository
import ic218.ru.rshb.utils.NfcManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository,
    private val nfcManager: NfcManager
) : ViewModel() {

    private val _state = MutableStateFlow<AuthScreenState>(AuthScreenState.Idle)
    val uiState: StateFlow<AuthScreenState> = _state.asStateFlow()

    private val _navigateToChannel = Channel<AuthCommand>()
    val navigateToChannel = _navigateToChannel.receiveAsFlow()

    private var isNfcInitialization = false

    init {
        nfcManager.addResultListener { result ->
            when (result) {
                is NfcManager.Result.Inited -> {
                    isNfcInitialization = result.isInit

                    if (result.isInit) {
                        _state.update {
                            AuthScreenState.Start
                        }
                    } else {

                    }
                }

                is NfcManager.Result.Success -> {
                    _state.update {
                        AuthScreenState.AuthNetwork
                    }
                    authNetwork(result.serial)
                }

                is NfcManager.Result.Error -> {
                    _state.update {
                        AuthScreenState.Error
                    }
                }
            }
        }

        nfcManager.sendCommand(NfcManager.Command.Init)
    }

    fun startNfc() {
        if (isNfcInitialization) nfcManager.sendCommand(NfcManager.Command.EnableForeground)
    }

    fun stopNfc() {
        if (isNfcInitialization) nfcManager.sendCommand(NfcManager.Command.DisableForeground)
    }

    private fun authNetwork(nfcId: String) {
        viewModelScope.launch {
            authRepository.login("123")
                .catch {
                    _state.update {
                        AuthScreenState.Error
                    }
                }
                .collect {
                    when (it) {
                        is ApiResult.Error -> {
                            _state.update {
                                AuthScreenState.ErrorAuth
                            }
                        }

                        is ApiResult.Success -> {
                            prefsRepository.setLogged(true)
                            prefsRepository.setLoggedUserId(it.data.id)
                            _state.update {
                                AuthScreenState.Done
                            }
                            _navigateToChannel.send(AuthCommand.SyncDb)
                            delay(500)
                            _navigateToChannel.send(AuthCommand.NavToMain)
                        }
                    }
                }
        }
    }
}