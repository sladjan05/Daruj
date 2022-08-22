package net.jsoft.daruj.auth.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.utils.clickableIf
import net.jsoft.daruj.common.utils.value

@Composable
fun NumberKeyboard(
    onNumberClick: (number: Int) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier.testTag(MainTestTags.NUMBER_KEYBOARD),
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RectangleShape
                )
                .padding(5.dp),
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
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RectangleShape
                                )
                                .clickableIf(
                                    clickable = number != null,
                                    shape = MaterialTheme.mShapes.small
                                ) {
                                    onNumberClick(number!!)
                                }
                                .clickableIf(
                                    clickable = (number == null) && (index == 11),
                                    shape = MaterialTheme.mShapes.small,
                                    onClick = onDeleteClick
                                )
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (number == null) {
                                if (index == 11) {
                                    Icon(
                                        imageVector = Icons.Default.Backspace,
                                        contentDescription = R.string.tx_delete.value,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            } else {
                                Text(
                                    text = number.toString(),
                                    color = MaterialTheme.colorScheme.onSurfaceDim,
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