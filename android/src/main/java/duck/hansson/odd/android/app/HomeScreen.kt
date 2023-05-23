package duck.hansson.odd.android.app

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import duck.hansson.odd.shared.viewmodel.AppViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(viewModel: AppViewModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SearchScreen(viewModel = viewModel)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(all = 16.dp),
            contentPadding = it,
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            item {
                Card {
                    Column(
                        modifier = Modifier.padding(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
                    ) {
                        Text(text = "Sök och leta efter katter inom Jordbruksverkets nya kattregister.")
                        Text(text = "Endast katter från Jordbruksverkets nya kattregister visas.")
                    }
                }
            }

            item {
                Card {
                    Column {
                        Text(
                            text = "Har du hittat en bortsprungen katt men är osäker på vad som bör göras?",
                            modifier = Modifier.padding(
                                top = 16.dp,
                                end = 16.dp,
                                start = 16.dp
                            )
                        )
                        ListItem(
                            headlineContent = { Text(text = "Information från polisen") },
                            modifier = Modifier.clickable {
                                val url = "https://polisen.se/tjanster-tillstand/hittegods/upphittat-djur"
                                val intent = CustomTabsIntent.Builder()
                                    .build()
                                intent.launchUrl(context, Uri.parse(url))
                            },
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                }
            }
        }
    }
}
