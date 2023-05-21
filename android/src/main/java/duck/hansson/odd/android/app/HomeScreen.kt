package duck.hansson.odd.android.app

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import duck.hansson.odd.shared.viewmodel.AppViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(viewModel: AppViewModel) {
    Scaffold(
        topBar = {
            SearchScreen(viewModel = viewModel)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(all = 16.dp),
            contentPadding = it
        ) {
            item {
                ErrorMessage(text = "Ingen sökning har gjorts.")
            }
        }
    }
}
