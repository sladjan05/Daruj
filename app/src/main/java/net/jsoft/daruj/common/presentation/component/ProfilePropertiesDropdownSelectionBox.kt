package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.clickable

private val ITEM_SIZE = 60.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfilePropertiesDropdownSelectionBox(
    selectedIndex: Int,
    label: String,
    items: List<@Composable BoxScope.() -> Unit>,
    rows: Int,
    columns: Int,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    labelColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {},
    onSelected: (index: Int) -> Unit = {}
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(targetState = expanded) { targetExpanded ->
            if (targetExpanded) {
                val shape = RoundedCornerShape(ITEM_SIZE / 2)
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(shape)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = shape
                        )
                        .padding(2.dp)
                ) {
                    for (row in 0 until rows) {
                        Row(
                            modifier = Modifier.wrapContentSize()
                        ) {
                            for (column in 0 until columns) {
                                val index = column + row * columns
                                if (index >= items.size) break

                                Box(
                                    modifier = Modifier
                                        .size(ITEM_SIZE)
                                        .clip(MaterialTheme.shape.rounded100p)
                                        .background(
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = MaterialTheme.shape.rounded100p
                                        )
                                        .clickable(
                                            shape = MaterialTheme.shape.rounded100p
                                        ) {
                                            onSelected(index)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    items[index]()
                                }
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(ITEM_SIZE)
                        .clip(MaterialTheme.shape.rounded100p)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shape.rounded100p
                        )
                        .clickable(
                            shape = MaterialTheme.shape.rounded100p,
                            onClick = onClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    items[selectedIndex]()
                }
            }
        }

        AnimatedContent(targetState = label) { targetLabel ->
            Text(
                text = targetLabel,
                modifier = Modifier.padding(top = 4.dp),
                color = labelColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun ProfilePropertiesDropdownSelectionBoxPreview() {
    val items = mutableListOf<@Composable BoxScope.() -> Unit>()

    for (i in 1..20) {
        items.add {
            Text(
                text = Char(i + 64).toString()
            )
        }
    }

    var index by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    DarujTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ProfilePropertiesDropdownSelectionBox(
                selectedIndex = index,
                label = "Krvna grupa",
                items = items,
                rows = 4,
                columns = 5,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .zIndex(1f)
                    .align(Alignment.CenterEnd),
                expanded = expanded,
                onClick = {
                    expanded = !expanded
                },
                onSelected = { newIndex ->
                    index = newIndex
                    expanded = false
                }
            )

            Text(
                text = "lalalal",
                modifier = Modifier.align(Alignment.TopCenter)
            )

        }
    }
}