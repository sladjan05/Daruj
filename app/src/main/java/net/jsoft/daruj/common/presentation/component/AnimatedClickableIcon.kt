package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import net.jsoft.daruj.common.util.indicationlessClickable
import net.jsoft.daruj.common.util.rememberMutableStateOf

@Composable
fun AnimatedClickableIcon(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = LocalContentColor.current,
    scaleTo: Float = 1.2f
) {
    var scale by rememberMutableStateOf(1f)
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        finishedListener = { scale = 1f }
    )

    AnimatedVisibility(
        visible = enabled,
        modifier = modifier,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .indicationlessClickable {
                    onClick()
                    scale = scaleTo
                }
                .scale(animatedScale),
            tint = tint
        )
    }
}