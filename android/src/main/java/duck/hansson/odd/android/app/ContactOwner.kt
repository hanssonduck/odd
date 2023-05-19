@file:OptIn(ExperimentalMaterial3Api::class)

package duck.hansson.odd.android.app

import android.telephony.PhoneNumberUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import duck.hansson.odd.shared.data.Owner
import duck.hansson.odd.shared.module.ContactActions
import java.util.Locale

@Composable
fun ContactOwner(
    actions: ContactActions,
    owner: Owner,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        ModalBottomSheet(
            onDismissRequest = onClose,
            modifier = modifier
        ) {
            Text(
                text = owner.fullName,
                modifier = Modifier.padding(all = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            owner.location?.let {
                ListItem(
                    modifier = Modifier.padding(bottom = 24.dp),
                    headlineContent = { Text(text = it.address) },
                    supportingContent = { Text("${it.zip} ${it.city}") }
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                owner.phoneNumber?.let {
                    val formatted = PhoneNumberUtils.formatNumber(
                        it,
                        Locale.getDefault().country
                    )
                    ContactAction(
                        label = "Ring $formatted",
                        onAction = { actions.call(phoneNumber = it) }
                    )
                }

                owner.email?.let {
                    ContactAction(
                        label = "Meddela $it",
                        onAction = { actions.email(to = it) }
                    )
                }

                if (owner.noContacts) {
                    Text(
                        text = "Den här personen har inte lagt till några kontaktsätt.",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ContactAction(
    label: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onAction,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        border = CardDefaults.outlinedCardBorder(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}
