package net.jsoft.daruj.common.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

val PhoneShapes = Shapes(
    small = RoundedCornerShape(5.dp),
    medium = RoundedCornerShape(10.dp)
)

val Shapes.full: RoundedCornerShape
    get() = RoundedCornerShape(100)

val LocalShapes = compositionLocalOf { PhoneShapes }

val MaterialTheme.mShapes: Shapes
    @Composable
    @ReadOnlyComposable
    get() = LocalShapes.current