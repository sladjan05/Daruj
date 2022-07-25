package net.jsoft.daruj.auth.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.clickableIf

private val HEIGHT = 60.dp
private val MAX_WIDTH = 400.dp

private val PADDING = 5.dp

@Composable
fun BoxScope.NumberKeyboard(
    visible: Boolean = true,
    onNumber: (number: Int) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = visible,
        modifier = Modifier.align(Alignment.BottomCenter),
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = MAX_WIDTH)
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RectangleShape
                )
                .padding(PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(4) { y ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(3) { x ->
                        val index = x + y * 3
                        val number = when (index) {
                            in 0..8 -> index + 1
                            10 -> 0
                            else -> null
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .height(HEIGHT)
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RectangleShape
                                )
                                .clickableIf(
                                    clickable = number != null,
                                    shape = MaterialTheme.shape.rounded5
                                ) {
                                    onNumber(number!!)
                                }
                                .clickableIf(
                                    clickable = (number == null) && (index == 11),
                                    shape = MaterialTheme.shape.rounded5,
                                    onClick = onDelete
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (number == null) {
                                if (index == 11) {
                                    Icon(
                                        imageVector = Icons.Default.Backspace,
                                        contentDescription = "Delete",
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceDim
                                    )
                                }
                            } else {
                                Text(
                                    text = number.toString(),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}