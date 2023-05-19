@file:OptIn(ExperimentalMaterial3Api::class)

package duck.hansson.odd.android.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import duck.hansson.odd.shared.viewmodel.AppState
import duck.hansson.odd.shared.viewmodel.AppViewModel

@Composable
fun SearchScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val focusManger = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val state by viewModel.state.collectAsStateWithLifecycle()
    var active by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .semantics { isContainer = true }
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = { viewModel.updateQuery(query = it) },
            onSearch = { focusManger.clearFocus() },
            active = active,
            onActiveChange = {
                active = it
                if (!it) {
                    viewModel.clearData()
                }
            },
            modifier = Modifier
                .align(Alignment.Center)
                .focusRequester(focusRequester = focusRequester),
            placeholder = { Text(text = "Chipnummer eller tatuering") },
            leadingIcon = {
                ActionButton(
                    active = active,
                    onClear = {
                        active = false
                        viewModel.clearData()
                    },
                    onFocus = {
                        active = true
                    }
                )
            },
            trailingIcon = {
                ClearButton(
                    active = state !is AppState.Waiting,
                    onClear = {
                        viewModel.updateQuery(query = "")
                        focusManger.moveFocus(focusDirection = FocusDirection.Previous)
                    }
                )
            },
            colors = SearchBarDefaults.colors(dividerColor = Color.Transparent)
        ) {
            SearchContent(
                state = state,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun ActionButton(
    active: Boolean,
    onClear: () -> Unit,
    onFocus: () -> Unit
) {
    IconButton(
        onClick = {
            if (active) {
                onClear()
            } else {
                onFocus()
            }
        },
    ) {
        Crossfade(
            targetState = active,
            label = "SearchBarActionButton"
        ) {
            if (it) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun ClearButton(
    active: Boolean,
    onClear: () -> Unit
) {
    AnimatedVisibility(
        visible = active,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        IconButton(
            onClick = {
                onClear()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    }
}
