package net.jsoft.daruj.common.presentation.component

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.jsoft.daruj.common.presentation.ui.theme.DarujTheme
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.Country
import net.jsoft.daruj.common.util.clickable

private val HEIGHT = 40.dp
private val BOX_HORIZONTAL_PADDING = 12.dp
private val TEXT_HORIZONTAL_PADDING = 30.dp

private val DROPDOWN_ICON_SIZE = 30.dp

private val MAX_LIST_HEIGHT = 300.dp
private val LIST_TOP_PADDING = 8.dp

@Composable
fun DropdownSelectionBox(
    text: String,
    modifier: Modifier = Modifier,
    items: List<String>? = null,
    expanded: Boolean = false,
    onClick: () -> Unit = {},
    onSelected: (index: Int) -> Unit = {}
) {
    val itemsNotEmpty = items != null && items.isNotEmpty()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEIGHT)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shape.rounded10
                )
                .clickable(
                    shape = MaterialTheme.shape.rounded10,
                    onClick = onClick
                )
                .padding(horizontal = BOX_HORIZONTAL_PADDING)
        ) {
            if (itemsNotEmpty) {
                val rotation: Float by animateFloatAsState(
                    targetValue = if (expanded) 180f else 0f
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
                    .align(Alignment.Center)
                    .padding(horizontal = TEXT_HORIZONTAL_PADDING),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelMedium
            )
        }

        AnimatedVisibility(
            visible = expanded && itemsNotEmpty
        ) {
            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()

            LaunchedEffect(text) {
                scope.launch {
                    val index = items?.indexOf(text)

                    if (index != null && index != -1) {
                        listState.scrollToItem(index)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = MAX_LIST_HEIGHT)
                    .padding(top = LIST_TOP_PADDING)
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
                            .then(
                                if (isCurrentItem) {
                                    Modifier
                                } else {
                                    Modifier.clickable(
                                        shape = RectangleShape,
                                        onClick = {
                                            onSelected(index)
                                        }
                                    )
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

@Preview
@Composable
fun DropdownSelectionBoxPreview() {
    DarujTheme {
        var expanded by remember { mutableStateOf(false) }
        var current by remember { mutableStateOf("Afganistan") }

        val countries = Country.values()
        val countryNames = countries.map { country -> stringResource(country.resId) }

        DropdownSelectionBox(
            text = current,
            items = countryNames,
            modifier = Modifier.width(350.dp),
            expanded = expanded,
            onClick = {
                expanded = !expanded
            },
            onSelected = { index ->
                current = countryNames[index]
                expanded = false
            }
        )
    }
}