package net.jsoft.daruj.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.File
import java.util.*
import kotlin.reflect.KProperty1

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

fun <T : Any> T.asMap(vararg additional: Pair<String, Any>): Map<String, Any?> {
    val map = mutableMapOf<String, Any?>()

    for (member in this::class.members) {
        if (member !is KProperty1<*, *>) continue
        member as KProperty1<T, *>

        map[member.name] = member.get(this)
    }

    map.putAll(additional.toMap())

    return map.toMap()
}

fun Bitmap.toUri(context: Context): Uri {
    val output = "${UUID.randomUUID()}.png"
    val outputFile = File(context.cacheDir, output)
    val outputStream = outputFile.outputStream()

    compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.close()

    return Uri.fromFile(outputFile)
}