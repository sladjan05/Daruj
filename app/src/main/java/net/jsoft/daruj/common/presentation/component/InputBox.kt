package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.mShapes

@Composable
fun InputBox(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.mShapes.medium,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = shape
            )
            .padding(
                vertical = 8.dp,
                horizontal = 14.dp
            ),
        contentAlignment = contentAlignment,
        content = content
    )
}