package net.jsoft.daruj.common.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

open class Spacing(
    val tiny: Dp,
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val huge: Dp
)

val PhoneSpacing = Spacing(
    tiny = 5.dp,
    extraSmall = 10.dp,
    small = 13.dp,
    medium = 20.dp,
    large = 30.dp,
    extraLarge = 40.dp,
    huge = 70.dp
)

val LocalSpacing = compositionLocalOf { PhoneSpacing }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current