package ic218.ru.rshb.feature.auth

import androidx.compose.runtime.Immutable
import ic218.ru.rshb.domain.entity.Task

sealed interface AuthScreenState {
    @Immutable
    data object Idle : AuthScreenState

    @Immutable
    data object Start : AuthScreenState

    @Immutable
    data object AuthNetwork : AuthScreenState

    @Immutable
    data object Error : AuthScreenState

    @Immutable
    data object ErrorAuth : AuthScreenState

    @Immutable
    data object Done : AuthScreenState
}