package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay
import net.jsoft.daruj.common.presentation.ui.theme.full
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.util.clickableIf
import net.jsoft.daruj.common.util.px
import net.jsoft.daruj.common.util.rememberMutableStateOf
import net.jsoft.daruj.common.util.thenIf

private val ELEMENT_SIZE = 60.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfilePropertiesSelectionBox(
    selectedIndex: Int,
    label: String,
    rows: Int,
    columns: Int,
    onClick: () -> Unit,
    onSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    enabled: Boolean = true,
    expandToRight: Boolean = true,
    labelColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable BoxScope.(index: Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            ProfileProperty(
                modifier = Modifier
                    .clickableIf(
                        clickable = enabled,
                        shape = MaterialTheme.mShapes.full,
                        onClick = onClick
                    )
            ) {
                content(selectedIndex)
            }

            val padding = 2.dp

            Popup(
                properties = PopupProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                ),
                offset = IntOffset(
                    x = if (expandToRight) 0 else -(ELEMENT_SIZE * (columns - 1) + padding * 2).px,
                    y = -(ELEMENT_SIZE * (rows - 1) + padding * 2).px
                )
            ) {
                val animationDuration = 200
                val animationSpec = tween<Float>(animationDuration)

                var pivotX = 1f - 1f / (2 * columns)
                val pivotY = 1f - 1f / (2 * rows)
                if (expandToRight) pivotX = 1f - pivotX

                val origin = TransformOrigin(
                    pivotFractionX = pivotX,
                    pivotFractionY = pivotY
                )

                val shape = RoundedCornerShape(ELEMENT_SIZE / 2)
                var realExpanded by rememberMutableStateOf(true)

                LaunchedEffect(expanded) {
                    delay(animationDuration.toLong())
                    realExpanded = expanded
                }

                Column(
                    modifier = Modifier
                        .thenIf(
                            statement = realExpanded,
                            ifModifier = Modifier
                                .width(ELEMENT_SIZE * columns + padding * 2)
                                .height(ELEMENT_SIZE * rows + padding * 2)
                        )
                ) {
                    AnimatedVisibility(
                        visible = expanded,
                        enter = scaleIn(
                            animationSpec = animationSpec,
                            transformOrigin = origin
                        ) + fadeIn(animationSpec),
                        exit = scaleOut(
                            animationSpec = animationSpec,
                            transformOrigin = origin
                        ) + fadeOut(animationSpec)
                    ) {
                        Grid(
                            rows = rows,
                            columns = columns,
                            modifier = Modifier
                                .shadow(
                                    elevation = 6.dp,
                                    shape = shape
                                )
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = shape
                                )
                                .clip(shape)
                                .padding(padding)
                        ) { index ->
                            ProfileProperty(
                                modifier = Modifier
                                    .clickableIf(
                                        clickable = enabled,
                                        shape = MaterialTheme.mShapes.full
                                    ) {
                                        onSelected(index)
                                    }
                            ) {
                                content(index)
                            }
                        }
                    }
                }
            }
        }

        Text(
            text = label,
            modifier = Modifier.padding(top = 4.dp),
            color = labelColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun ProfileProperty(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .size(ELEMENT_SIZE)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.mShapes.full
            )
            .clip(MaterialTheme.mShapes.full)
            .then(modifier),
        contentAlignment = Alignment.Center,
        content = content
    )
}
