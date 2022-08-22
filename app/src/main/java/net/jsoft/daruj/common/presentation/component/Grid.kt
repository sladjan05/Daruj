package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Grid(
    rows: Int,
    columns: Int,
    modifier: Modifier = Modifier,
    content: @Composable (index: Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        repeat(rows) { row ->
            Row {
                repeat(columns) { column ->
                    content(column + row * columns)
                }
            }
        }
    }
}