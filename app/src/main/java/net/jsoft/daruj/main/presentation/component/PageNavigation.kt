package net.jsoft.daruj.main.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.mShapes
import net.jsoft.daruj.common.util.indicationlessClickable
import net.jsoft.daruj.common.util.rememberMutableStateOf
import net.jsoft.daruj.common.util.toDp

@Composable
fun PageNavigation(
    vararg pages: String,
    currentPage: Int,
    onPageChange: (page: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var maxWidth by rememberMutableStateOf(0.dp)

    // Should use BoxWithConstraints, but BoxWithConstraints' border modifier has a bug
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.mShapes.medium
            )
            .clip(MaterialTheme.mShapes.medium)
            .onSizeChanged { size -> maxWidth = size.width.toDp(context) }
    ) {
        var height by rememberMutableStateOf(0.dp)
        val offset by animateDpAsState(targetValue = maxWidth / pages.size * currentPage)

        Box(
            modifier = Modifier
                .fillMaxWidth(1f / pages.size)
                .height(height)
                .offset(x = offset)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RectangleShape
                )
        )

        Row(
            modifier = Modifier
                .width(maxWidth)
                .onSizeChanged { size -> height = size.height.toDp(context) }
        ) {
            repeat(pages.size) { page ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .indicationlessClickable {
                            onPageChange(page)
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val color by animateColorAsState(
                        targetValue = if (page == currentPage) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    )

                    Text(
                        text = pages[page],
                        color = color,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}