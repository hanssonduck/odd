@file:OptIn(FlowPreview::class)

package duck.hansson.odd.shared.viewmodel

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import duck.hansson.odd.shared.module.ContactActions
import duck.hansson.odd.shared.repository.SearchRepository
import duck.hansson.odd.shared.repository.SearchResult
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val searchRepository: SearchRepository,
    val contactActions: ContactActions
) : KMMViewModel() {
    private val query = MutableStateFlow<String?>(
        viewModelScope = viewModelScope,
        value = null
    )

    private val results = MutableStateFlow<PersistentList<SearchResult>?>(
        viewModelScope = viewModelScope,
        value = null
    )

    init {
        viewModelScope.coroutineScope.launch {
            query
                .filterNotNull()
                .filter { it.isNotBlank() }
                .debounce(timeoutMillis = 250)
                .collectLatest { query ->
                    results.update {
                        searchRepository.find(query = query)
                    }
                }
        }
    }

    @NativeCoroutinesState
    val state = combine(query, results) { query, results ->
        when {
            query.isNullOrBlank() -> AppState.Waiting()
            results == null -> AppState.Waiting(query = query)
            results.isEmpty() -> AppState.Empty(query = query)
            else -> AppState.Data(
                query = query,
                results = results
            )
        }
    }.stateIn(
        viewModelScope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AppState.Waiting()
    )

    fun updateQuery(query: String) {
        if (query.isBlank()) {
            results.update { null }
        }
        this.query.update { query }
    }

    fun clearData() {
        query.update { null }
        results.update { null }
    }
}

sealed class AppState {
    abstract val key: String
    abstract val query: String

    data class Waiting(
        override val key: String = "waiting",
        override val query: String = ""
    ) : AppState()
    data class Empty(
        override val key: String = "empty",
        override val query: String
    ) : AppState()
    data class Data(
        override val key: String = "data",
        override val query: String,
        val results: PersistentList<SearchResult>
    ) : AppState()
}
