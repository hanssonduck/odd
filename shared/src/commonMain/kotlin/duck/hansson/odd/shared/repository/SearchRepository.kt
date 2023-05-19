package duck.hansson.odd.shared.repository

import duck.hansson.odd.shared.data.Animal
import duck.hansson.odd.shared.data.Owner
import duck.hansson.odd.shared.data.RemoteAnimal
import duck.hansson.odd.shared.data.RemoteOwner
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Resource("/sokdjur/{query}")
private data class SearchResource(val query: String)

@Serializable
data class RemoteSearchResult(
    @SerialName("farvisas")
    val hidden: Boolean,
    @SerialName("handlaggningPagar")
    val processing: Boolean,
    @SerialName("djur")
    val animal: RemoteAnimal,
    @SerialName("agare")
    val owner: RemoteOwner
) {
    fun toSearchResult() = SearchResult(
        animal = this.animal.toAnimal(),
        owner = this.owner.toOwner()
    )
}

data class SearchResult(
    val owner: Owner,
    val animal: Animal
)

class SearchRepository(private val httpClient: HttpClient) {
    suspend fun find(query: String): PersistentList<SearchResult> {
        val response = httpClient.get(resource = SearchResource(query))
        val results = response.body<List<RemoteSearchResult>>()

        return results
            .filter { it.hidden || it.processing }
            .map { it.toSearchResult() }
            .toPersistentList()
    }
}
