package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.MainTestTags

private val HEIGHT = 60.dp

@Composable
fun BoxScope.TextSnackbar(
    hostState: SnackbarHostState,
    isError: Boolean = false
) {
    SnackbarHost(
        hostState = hostState,
        modifier = Modifier
            .testTag(
                if (isError)
                    MainTestTags.Snackbar.ERROR
                else
                    MainTestTags.Snackbar.INFO
            )
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
                containerColor = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.surface
                },
            ) {
                Text(
                    text = snackbarData.visuals.message,
                    modifier = Modifier.testTag(MainTestTags.Snackbar.TEXT),
                    color = if (isError) {
                        MaterialTheme.colorScheme.onError
                    } else {
                        MaterialTheme.colorScheme.onSurfaceDim
                    },
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    )
}