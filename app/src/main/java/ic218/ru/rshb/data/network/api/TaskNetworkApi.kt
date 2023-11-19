package ic218.ru.rshb.data.network.api

import ic218.ru.rshb.data.network.ApiError
import ic218.ru.rshb.data.network.ApiResult
import ic218.ru.rshb.data.network.entity.TaskNetworkEntity
import ic218.ru.rshb.domain.entity.TaskField
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow

class TaskNetworkApi(private val httpClient: HttpClient) {

    fun getTasks() = flow<ApiResult<List<TaskNetworkEntity>>> {
        try {
            emit(ApiResult.Success(httpClient.get("/v1/quotes").body()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(ApiError.UnknownError(e.message ?: "Something went wrong")))
        }
    }

    fun getTaskFields() = flow<ApiResult<List<TaskField>>> {
        try {
            emit(ApiResult.Success(httpClient.get("/v1/quotes").body()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(ApiError.UnknownError(e.message ?: "Something went wrong")))
        }
    }
}