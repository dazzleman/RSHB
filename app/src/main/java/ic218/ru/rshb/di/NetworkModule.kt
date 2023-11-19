package ic218.ru.rshb.di

import ic218.ru.rshb.data.network.api.AuthNetworkApi
import ic218.ru.rshb.data.network.api.ProfileNetworkApi
import ic218.ru.rshb.data.network.api.TaskNetworkApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val BASE_URL = "http://api.foralabs.ru:5000"

val networkModule = module {

    single {
        HttpClient(OkHttp) {
            engine {
                config {
                    followRedirects(true)
                }
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("Authorization", "Bearer 123")
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    single { AuthNetworkApi(get()) }
    single { ProfileNetworkApi(get()) }
    single { TaskNetworkApi(get()) }
}