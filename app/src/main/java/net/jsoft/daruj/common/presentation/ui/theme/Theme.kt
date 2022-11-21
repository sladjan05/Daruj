package net.jsoft.daruj.common.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = Blue13,
    onPrimary = Color.White,

    secondary = Red26,

    background = Color.White,
    onBackground = Gray50,
    // onBackgroundDim = Gray28,

    surface = Gray91,
    onSurface = Gray52,
    // onSurfaceDim = Gray28,

    error = Red66,
    onError = Color.White,

    outline = Gray78
)

val ColorScheme.onBackgroundDim: Color
    get() = Gray28

val ColorScheme.onSurfaceDim: Color
    get() = Gray28

val ColorScheme.onSurfaceLight: Color
    get() = Gray78

@Composable
fun DarujTheme(
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)

    CompositionLocalProvider(
        LocalSpacing provides PhoneSpacing,
        LocalShapes provides PhoneShapes
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = DarujTypography,
            content = content
        )
    }
}