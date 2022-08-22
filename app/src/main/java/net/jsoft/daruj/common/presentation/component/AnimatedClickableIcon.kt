package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import net.jsoft.daruj.common.utils.indicationlessClickable
import net.jsoft.daruj.common.utils.rememberMutableStateOf

@Composable
fun AnimatedClickableIcon(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    scaleTo: Float = 1.2f
) {
    var scale by rememberMutableStateOf(1f)
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        finishedListener = {
            scale = 1f
        }
    )

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .indicationlessClickable {
                onClick()
                scale = scaleTo
            }
            .scale(animatedScale),
        tint = tint
    )
}