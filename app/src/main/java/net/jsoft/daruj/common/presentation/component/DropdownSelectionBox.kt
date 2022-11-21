package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.MainTestTags
import net.jsoft.daruj.common.misc.PopupTestTags
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.spacing
import net.jsoft.daruj.common.util.clickableIf
import net.jsoft.daruj.common.util.px
import net.jsoft.daruj.common.util.rememberMutableStateOf
import net.jsoft.daruj.common.util.thenIf

@Composable
fun DropdownSelectionBox(
    text: String,
    items: List<String>,
    onClick: () -> Unit,
    onSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    enabled: Boolean = true
) {
    var pxHeight by rememberMutableStateOf(0)

    BoxWithConstraints(
        modifier = modifier
            .onSizeChanged { size ->
                pxHeight = size.height
            }
    ) {
        InputBox(
            modifier = Modifier
                .fillMaxWidth()
                .clickableIf(
                    clickable = enabled,
                    shape = MaterialTheme.mShapes.medium,
                    onClick = onClick
                )
        ) {
            if (items.isNotEmpty()) {
                val rotation: Float by animateFloatAsState(
                    targetValue = if (expanded && enabled) 180f else 0f
                )

                Icon(
                    painter = painterResource(R.drawable.ic_down),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                        .offset(
                            x = 8.dp,
                            y = 1.dp
                        )
                        .rotate(rotation)
                        .align(Alignment.CenterStart),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = text,
                modifier = Modifier
                    .testTag(MainTestTags.Dropdown.TEXT)
                    .padding(horizontal = 30.dp)
                    .align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurfaceDim
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                style = MaterialTheme.typography.labelMedium
            )
        }

        Popup(
            offset = IntOffset(
                x = 0,
                y = pxHeight + MaterialTheme.spacing.extraSmall.px
            ),
            properties = PopupProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            )
        ) {
            val animationDuration = 200
            val animationSpec = tween<IntSize>(animationDuration)
            var realExpanded by rememberMutableStateOf(true)

            LaunchedEffect(expanded) {
                delay(animationDuration.toLong())
                realExpanded = expanded
            }

            Box(
                modifier = Modifier
                    .thenIf(
                        statement = realExpanded || expanded,
                        ifModifier = Modifier.width(this@BoxWithConstraints.maxWidth)
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                AnimatedVisibility(
                    visible = expanded && enabled && items.isNotEmpty(),
                    modifier = Modifier
                        .thenIf(
                            statement = expanded,
                            ifModifier = Modifier
                                .shadow(
                                    elevation = 6.dp,
                                    shape = MaterialTheme.mShapes.medium
                                )
                        ),
                    enter = expandVertically(animationSpec),
                    exit = shrinkVertically(animationSpec)
                ) {
                    val listState = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier
                            .testTag(PopupTestTags.Dropdown.LIST)
                            .fillMaxWidth()
                            .heightIn(max = 200.dp)
                            .clip(MaterialTheme.mShapes.medium),
                        state = listState,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(count = items.size) { index ->
                            val isCurrentItem = items[index] == text

                            InputBox(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickableIf(
                                        clickable = !isCurrentItem,
                                        shape = RectangleShape,
                                        onClick = {
                                            onSelected(index)
                                        }
                                    ),
                                shape = RectangleShape,
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = items[index],
                                    color = if (isCurrentItem) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceDim
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}