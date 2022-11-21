package net.jsoft.daruj.common.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.component.TextSnackbar
import net.jsoft.daruj.common.util.rememberSnackbarHostState

@Composable
fun ScreenWithSnackbars(
    modifier: Modifier = Modifier,
    infoHostState: SnackbarHostState = rememberSnackbarHostState(),
    errorHostState: SnackbarHostState = rememberSnackbarHostState(),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        content()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.BottomStart)
        ) {
            TextSnackbar(hostState = infoHostState)

            TextSnackbar(
                hostState = errorHostState,
                color = MaterialTheme.colorScheme.error,
                textColor = MaterialTheme.colorScheme.onError
            )
        }
    }
}