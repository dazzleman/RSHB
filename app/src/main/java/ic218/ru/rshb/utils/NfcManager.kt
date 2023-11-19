package ic218.ru.rshb.utils

class NfcManager {

    private val commandListener = mutableListOf<CommandListener>()
    private val resultListener = mutableListOf<ResultListener>()

    fun addCommandListener(listener: CommandListener) {
        commandListener.add(listener)
    }

    fun addResultListener(listener: ResultListener) {
        resultListener.add(listener)
    }

    fun clearListeners() {
        commandListener.clear()
        resultListener.clear()
    }

    fun sendCommand(command: Command) {
        commandListener.forEach { it.invoke(command) }
    }

    fun sendResult(result: Result) {
        resultListener.forEach { it.invoke(result) }
    }

    fun interface CommandListener {
        fun invoke(command: Command)
    }

    fun interface ResultListener {
        fun invoke(result: Result)
    }

    sealed interface Command {
        object Init : Command
        object EnableForeground : Command
        object DisableForeground : Command
    }

    sealed interface Result {
        class Inited(val isInit: Boolean) : Result
        class Success(val serial: String) : Result
        object Error : Result
    }
}