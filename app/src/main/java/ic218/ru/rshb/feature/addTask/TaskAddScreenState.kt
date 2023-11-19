package ic218.ru.rshb.feature.addTask

import androidx.compose.runtime.Immutable

sealed interface TaskAddScreenState {
    @Immutable
    data object Empty : TaskAddScreenState
}