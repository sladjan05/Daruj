package net.jsoft.daruj.common.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Dp.px: Int
    @Composable
    get() = toPx(LocalContext.current)

fun Dp.toPx(context: Context): Int {
    val dpi = context.resources.displayMetrics.densityDpi
    return (this * dpi / 160).value.toInt()
}

@Composable
fun Int.toDp(): Dp = toDp(LocalContext.current)

fun Int.toDp(context: Context): Dp {
    val dpi = context.resources.displayMetrics.densityDpi
    return (this * 160 / dpi).dp
}