package ic218.ru.rshb.data.network.api

import ic218.ru.rshb.data.network.ApiError
import ic218.ru.rshb.data.network.ApiResult
import ic218.ru.rshb.domain.entity.Employer
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.flow

class ProfileNetworkApi(private val httpClient: HttpClient) {

    fun getUsers() = flow<ApiResult<List<Employer>>> {
        try {
            val result = httpClient.get("/api/employees")
            if (result.status == HttpStatusCode.OK) {
                emit(ApiResult.Success(result.body()))
            } else {
                emit(ApiResult.Error(ApiError.HttpError(result.status.value)))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(ApiError.UnknownError(e.message ?: "Something went wrong")))
        }
    }
}