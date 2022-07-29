package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape
import net.jsoft.daruj.common.util.thenIf

private val HEIGHT = 40.dp
private val BOX_HORIZONTAL_PADDING = 14.dp

@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    text: String = "",
    prefix: String = "",
    hint: String? = null,
    multiline: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    contentAlignment: Alignment = Alignment.CenterStart,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (value: String) -> Unit = {}
) {
    BasicTextField(
        value = text,
        modifier = modifier
            .thenIf(
                statement = !multiline,
                modifier = Modifier.height(HEIGHT)
            ),
        onValueChange = { value ->
            if (value.length <= maxLength) {
                onValueChange(value)
            }
        },
        enabled = enabled,
        singleLine = !multiline,
        textStyle = MaterialTheme.typography.labelMedium.copy(
            color = if (enabled) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceDim
            }
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceDim),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shape.rounded10
                )
                .padding(horizontal = BOX_HORIZONTAL_PADDING),
            contentAlignment = contentAlignment
        ) {
            if (hint != null && prefix.isEmpty() && text.isEmpty()) {
                Text(
                    text = hint,
                    color = MaterialTheme.colorScheme.onSurfaceDim,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Row(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width + 5, placeable.height) {
                            placeable.placeRelative(5, 0)
                        }
                    }
                    .widthIn(min = 0.dp, max = Dp.Infinity)
                    .horizontalScroll(
                        state = rememberScrollState(0),
                        enabled = false
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prefix,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceDim
                    },
                    style = MaterialTheme.typography.labelMedium
                )

                innerTextField()
            }
        }
    }
}