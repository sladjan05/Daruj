package net.jsoft.daruj.common.presentation.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import net.jsoft.daruj.common.presentation.ui.theme.onSurfaceDim
import net.jsoft.daruj.common.presentation.ui.theme.shape

private val HEIGHT = 40.dp
private val BOX_HORIZONTAL_PADDING = 12.dp

@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    text: String = "",
    prefix: String = "",
    hint: String? = null,
    multiline: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    contentAlignment: Alignment = Alignment.CenterStart,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (value: String) -> Unit = {},
    onCustomClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        modifier = modifier
            .height(HEIGHT),
        onValueChange = { value ->
            if (value.length <= maxLength) {
                onValueChange(value)
            }
        },
        singleLine = !multiline,
        textStyle = MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceDim),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shape.rounded10
                    )
                    .then(
                        if (onCustomClick != null) {
                            Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onCustomClick
                            )
                        } else {
                            Modifier
                        }
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
                        .width(IntrinsicSize.Min)
                        .then(object : LayoutModifier {
                            override fun MeasureScope.measure(
                                measurable: Measurable,
                                constraints: Constraints
                            ): MeasureResult {
                                val placeable = measurable.measure(constraints.offset(horizontal = 10))
                                return layout(placeable.width, placeable.height) {
                                    placeable.placeRelative(5, 0)
                                }
                            }
                        })
                        .widthIn(min = 0.dp, max = Dp.Infinity)
                        .horizontalScroll(
                            state = remember { ScrollState(0) },
                            enabled = false
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = prefix,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium
                    )

                    innerTextField()
                }
            }
        }
    )
}