package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.shape

private val HEIGHT = 60.dp

@Composable
fun BoxScope.TextSnackbar(
    hostState: SnackbarHostState,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    SnackbarHost(
        hostState = hostState,
        modifier = Modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .height(HEIGHT)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .align(Alignment.BottomCenter),
        snackbar = { snackbarData ->
            Snackbar(
                shape = MaterialTheme.shape.rounded10,
                containerColor = backgroundColor,
            ) {
                Text(
                    text = snackbarData.visuals.message,
                    color = textColor,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    )
}