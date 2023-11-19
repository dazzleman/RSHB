package ic218.ru.rshb.data.network

/*sealed class ApiResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(quotes: T) : ApiResult<T>(data = quotes)
    class Error<T>(error: String) : ApiResult<T>(error = error)
}*/

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val error: ApiError) : ApiResult<Nothing>()
}

sealed interface ApiError {
    object Network : ApiError
    class HttpError(val code: Int) : ApiError
    class UnknownError(val message: String) : ApiError
}
