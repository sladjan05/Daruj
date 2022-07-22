package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.shape

private val HEIGHT = 50.dp

@Composable
fun TextSnackbar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
            .widthIn(max = 400.dp)
            .height(HEIGHT),
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