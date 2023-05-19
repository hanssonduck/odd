package duck.hansson.odd.shared.module

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.scope.Scope

internal fun Scope.provideHttpClient() = HttpClient {
    install(plugin = Resources)
    install(plugin = ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }
    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "etjanst.sjv.se"
            path("tasspub/api/")
        }
        header(
            key = "X-Hashcash-Token",
            value = "1685708557458:67204:d8r72gj6n5:0000984ce89fb9ac75260ddb7bdfd2ceeff9e3f4ea0405b09bb8fcb4ad1808a3"
        )
    }
}

interface ContactActions {
    fun call(phoneNumber: String)
    fun email(to: String)
}

internal expect fun Scope.provideContactActions(): ContactActions
