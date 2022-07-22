package net.jsoft.daruj.common.presentation.ui.theme

import android.graphics.drawable.shapes.RectShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

data class Shape(
    val rounded5: RoundedCornerShape = RoundedCornerShape(5.dp),
    val rounded10: RoundedCornerShape = RoundedCornerShape(10.dp),
    val rounded100p: RoundedCornerShape = RoundedCornerShape(100),

    val bottomSheetDialog: RoundedCornerShape = RoundedCornerShape(
        topStart = 15.dp,
        topEnd = 15.dp
    )
)

val LocalShape = compositionLocalOf { Shape() }

val MaterialTheme.shape: Shape
    @Composable
    @ReadOnlyComposable
    get() = LocalShape.current