package net.jsoft.daruj.auth.presentation.component

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.shape

private val SIZE = 40.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VerificationCodeBox(
    modifier: Modifier = Modifier,
    code: String = "",
) {
    Row(
        modifier = modifier.wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(6) { i ->
            Box(
                modifier = Modifier
                    .size(SIZE)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shape.rounded10
                    ),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = if (i < code.length) code[i].toString() else "",
                    transitionSpec = {
                        val tweenSpec = tween<IntOffset>(durationMillis = 100)

                        fun slideInVertically(initialOffsetY: (fullHeight: Int) -> Int) = slideInVertically(
                            animationSpec = tweenSpec,
                            initialOffsetY = initialOffsetY
                        )

                        fun slideOutVertically(targetOffsetY: (fullHeight: Int) -> Int) = slideOutVertically(
                            animationSpec = tweenSpec,
                            targetOffsetY = targetOffsetY
                        )

                        if (targetState.isEmpty()) {
                            slideInVertically { -it } with slideOutVertically { it }
                        } else {
                            slideInVertically { it } with slideOutVertically { -it }
                        } using SizeTransform { _, _ ->
                            keyframes {
                                durationMillis = 100
                            }
                        }
                    }
                ) { targetText ->
                    Text(
                        text = targetText,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}