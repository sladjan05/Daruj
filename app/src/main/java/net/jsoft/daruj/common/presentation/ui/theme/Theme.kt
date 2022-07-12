package net.jsoft.daruj.common.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Blue13,
    onPrimary = Color.White,

    secondary = Red26,

    background = Color.White,
    onBackground = Gray28,
    // onBackgroundDim = Gray50,

    surface = Gray91,
    onSurface = Gray28,
    // onSurfaceDim = Gray52,

    error = Red66,
    onError = Color.White,

    outline = Gray78
)

val ColorScheme.onBackgroundDim: Color
    get() = Gray50

val ColorScheme.onSurfaceDim: Color
    get() = Gray52

@Composable
fun DarujTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalShape provides Shape()
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}