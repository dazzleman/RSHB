package ic218.ru.rshb.feature.tasks

import androidx.compose.runtime.Immutable
import ic218.ru.rshb.domain.entity.Task

sealed interface TasksScreenState {
    @Immutable
    data object Loading : TasksScreenState

    @Immutable
    data object Main : TasksScreenState
}