@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package duck.hansson.odd.android.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import duck.hansson.odd.shared.data.Animal
import duck.hansson.odd.shared.data.Owner
import duck.hansson.odd.shared.module.ContactActions
import duck.hansson.odd.shared.viewmodel.AppState
import duck.hansson.odd.shared.viewmodel.AppViewModel
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun SearchContent(
    state: AppState,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = state.key,
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(color = MaterialTheme.colorScheme.background),
        label = "SearchContent"
    ) {
        LazyColumn(
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            when {
                it == "data" && state is AppState.Data -> items(state.results) { result ->
                    ResultItem(
                        contactActions = viewModel.contactActions,
                        animal = result.animal,
                        owner = result.owner
                    )
                }

                it == "empty" && state is AppState.Empty -> item {
                    ErrorMessage(
                        text = "Inga katter hittades för denna sökning. Prova ett annat chipnummer eller tatuering."
                    )
                }

                it == "waiting" && state is AppState.Waiting -> item {
                    ErrorMessage(text = "Ingen sökning har gjorts.",)
                }
            }
        }
    }
}

@Composable
private fun ResultItem(
    contactActions: ContactActions,
    animal: Animal,
    owner: Owner
) {
    Card {
        AnimalHeader(
            name = animal.name,
            gender = animal.gender
        )

        animal.chip?.let {
            AnimalInfo(
                text = it,
                subtitle = "Chipnummer"
            )
        }

        animal.tattoo?.let {
            AnimalInfo(
                text = it,
                subtitle = "Tatuering"
            )
        }

        AnimalInfo(
            text = animal.color,
            subtitle = animal.breed
        )

        val formatted = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.LONG)
            .withZone(ZoneId.systemDefault())
            .format(animal.birthday.toJavaInstant())
        AnimalInfo(
            text = "${animal.age} år gammal",
            subtitle = "Född $formatted"
        )

        animal.overview?.let {
            AnimalOverview(overview = it)
        }

        ContactOwnerButton(
            contactActions = contactActions,
            owner = owner
        )
    }
}

@Composable
fun AnimalHeader(
    name: String,
    gender: String,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        supportingContent = {
            Text(
                text = gender,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

@Composable
private fun AnimalInfo(
    text: String,
    subtitle: String
) {
    ListItem(
        headlineContent = {
            Text(text = text)
        },
        supportingContent = {
            Text(text = subtitle)
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

@Composable
fun AnimalOverview(
    overview: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Text(
            text = overview,
            modifier = Modifier.padding(all = 16.dp),
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
private fun ContactOwnerButton(
    contactActions: ContactActions,
    owner: Owner
) {
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        ContactOwner(
            actions = contactActions,
            owner = owner,
            onClose = { showSheet = false }
        )
    }

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            onClick = { showSheet = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = 16.dp,
                    bottom = 16.dp,
                    start = 16.dp
                ),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            border = CardDefaults.outlinedCardBorder(),
            contentPadding = PaddingValues(all = 16.dp)
        ) {
            Text(
                text = "Kontakta ${owner.name.first}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
fun ErrorMessage(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
