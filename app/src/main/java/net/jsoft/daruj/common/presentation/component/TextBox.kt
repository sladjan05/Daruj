package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.utils.thenIf

@Composable
fun TextBox(
    text: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    prefix: String = "",
    hint: String = "",
    multiline: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    BasicTextField(
        value = text,
        modifier = modifier,
        onValueChange = { value ->
            if (value.length <= maxLength) {
                onValueChange(value)
            }
        },
        enabled = enabled,
        singleLine = !multiline,
        maxLines = Int.MAX_VALUE,
        textStyle = MaterialTheme.typography.labelMedium.copy(
            color = if (enabled) {
                MaterialTheme.colorScheme.onSurfaceDim
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    ) { innerTextField ->
        InputBox(
            modifier = Modifier
                .thenIf(
                    statement = multiline,
                    ifModifier = Modifier.fillMaxSize(),
                    elseModifier = Modifier.fillMaxWidth()
                ),
            contentAlignment = if (multiline) {
                Alignment.TopStart
            } else {
                Alignment.CenterStart
            }
        ) {
            if (hint.isNotBlank() && prefix.isEmpty() && text.isEmpty()) {
                Text(
                    text = hint,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Row(
                modifier = Modifier
                    .thenIf(
                        statement = maxLength != Int.MAX_VALUE && multiline,
                        ifModifier = Modifier.padding(bottom = 15.dp)
                    )
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width + 5, placeable.height) {
                            placeable.placeRelative(5, 0)
                        }
                    }
                    .widthIn(min = 0.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prefix,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurfaceDim
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    style = MaterialTheme.typography.labelMedium
                )

                innerTextField()
            }

            if (maxLength != Int.MAX_VALUE && multiline) {
                Text(
                    text = "${text.length}/$maxLength",
                    modifier = Modifier.align(Alignment.BottomEnd),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}