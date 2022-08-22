package net.jsoft.daruj.introduction.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.full
import net.jsoft.daruj.common.presentation.ui.theme.mShapes

private val WIDTH = 35.dp
private val HEIGHT = 5.dp

@Composable
fun PageIndicator(
    pageCount: Int,
    page: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(HEIGHT),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for(i in 0 until pageCount) {
            val color by animateColorAsState(
                targetValue = if (i == page) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )

            Box(
                modifier = Modifier
                    .width(WIDTH)
                    .fillMaxHeight()
                    .background(
                        color = color,
                        shape = MaterialTheme.mShapes.full
                    )
            )
        }
    }
}