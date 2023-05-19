package duck.hansson.odd.shared

import duck.hansson.odd.shared.module.initKoin
import duck.hansson.odd.shared.repository.SearchRepository
import kotlinx.coroutines.runBlocking
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class OddTest : KoinTest {
    private val searchRepository by inject<SearchRepository>()

    @BeforeTest
    fun setup() {
        initKoin { }
    }

    @Test
    fun testSearch() = runBlocking {
        val results = searchRepository.find(query = "1")
        assertTrue(results.first().animal.tattoo == "1")
    }
}
