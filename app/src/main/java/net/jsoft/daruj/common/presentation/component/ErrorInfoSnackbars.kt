package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorInfoSnackbars(
    infoHostState: SnackbarHostState,
    errorHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Box(modifier = modifier) {
        TextSnackbar(
            hostState = errorHostState,
            color = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.onError,
            textAlign = textAlign
        )

        TextSnackbar(
            hostState = infoHostState,
            textAlign = textAlign
        )
    }
}