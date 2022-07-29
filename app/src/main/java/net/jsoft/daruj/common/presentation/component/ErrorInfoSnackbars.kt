package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim

@Composable
fun BoxScope.ErrorInfoSnackbars(
    infoHostState: SnackbarHostState,
    errorHostState: SnackbarHostState
) {
    TextSnackbar(
        hostState = errorHostState,
        isError = true
    )

    TextSnackbar(
        hostState = infoHostState,
        isError = false
    )
}