package net.jsoft.daruj.common.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.component.ErrorInfoSnackbars

@Composable
fun ScreenWithSnackbars(
    infoHostState: SnackbarHostState,
    errorHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        content()

        ErrorInfoSnackbars(
            infoHostState = infoHostState,
            errorHostState = errorHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.BottomStart)
        )
    }
}