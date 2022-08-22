package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.presentation.ui.theme.mShapes

@Composable
fun TextSnackbar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign = TextAlign.Start
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier.widthIn(max = 400.dp)
    ) { snackbarData ->
        Snackbar(
            shape = MaterialTheme.mShapes.medium,
            containerColor = color
        ) {
            Text(
                text = snackbarData.visuals.message,
                modifier = Modifier
                    .testTag(MainTestTags.Snackbar.TEXT)
                    .fillMaxWidth(),
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = textAlign
            )
        }
    }
}