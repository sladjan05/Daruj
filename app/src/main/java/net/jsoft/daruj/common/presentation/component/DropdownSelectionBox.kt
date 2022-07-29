package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.MainTestTags
import net.jsoft.daruj.common.util.PopupTestTags
import net.jsoft.daruj.common.util.clickableIf

private val HEIGHT = 40.dp
private val BOX_HORIZONTAL_PADDING = 12.dp
private val TEXT_HORIZONTAL_PADDING = 30.dp

private val DROPDOWN_ICON_SIZE = 30.dp

private val MAX_LIST_HEIGHT = 200.dp

@Composable
fun DropdownSelectionBox(
    text: String,
    modifier: Modifier = Modifier,
    items: List<String>? = null,
    expanded: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onSelected: (index: Int) -> Unit = {}
) {
    val itemsNotEmpty = items != null && items.isNotEmpty()

    BoxWithConstraints(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEIGHT)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shape.rounded10
                )
                .clickableIf(
                    clickable = enabled,
                    shape = MaterialTheme.shape.rounded10,
                    onClick = onClick
                )
                .padding(horizontal = BOX_HORIZONTAL_PADDING)
        ) {
            if (itemsNotEmpty) {
                val rotation: Float by animateFloatAsState(
                    targetValue = if (expanded && enabled) 180f else 0f
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier
                        .rotate(rotation)
                        .size(DROPDOWN_ICON_SIZE)
                        .align(Alignment.CenterStart),
                    tint = MaterialTheme.colorScheme.onSurfaceDim
                )
            }

            Text(
                text = text,
                modifier = Modifier
                    .testTag(MainTestTags.Dropdown.TEXT)
                    .align(Alignment.Center)
                    .padding(horizontal = TEXT_HORIZONTAL_PADDING),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceDim
                },
                style = MaterialTheme.typography.labelMedium
            )
        }

        if (expanded && enabled && itemsNotEmpty) {
            Popup(
                offset = IntOffset(
                    x = 0,
                    y = 136
                ),
                properties = PopupProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            ) {
                val listState = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier
                        .testTag(PopupTestTags.Dropdown.LIST)
                        .width(this@BoxWithConstraints.maxWidth)
                        .heightIn(max = MAX_LIST_HEIGHT)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shape.rounded10
                        )
                        .clip(MaterialTheme.shape.rounded5),
                    state = listState,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(count = items!!.size) { index ->
                        val isCurrentItem = items[index] == text
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(HEIGHT)
                                .clickableIf(
                                    clickable = !isCurrentItem,
                                    shape = RectangleShape,
                                    onClick = {
                                        onSelected(index)
                                    }
                                )
                                .padding(horizontal = BOX_HORIZONTAL_PADDING),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = items[index],
                                color = if (isCurrentItem) {
                                    MaterialTheme.colorScheme.onSurfaceDim
                                } else {
                                    MaterialTheme.colorScheme.onSurface
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