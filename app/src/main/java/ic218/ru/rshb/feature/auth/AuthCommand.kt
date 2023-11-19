package ic218.ru.rshb.feature.auth

sealed interface AuthCommand {
    object NavToMain: AuthCommand
    object SyncDb: AuthCommand
}