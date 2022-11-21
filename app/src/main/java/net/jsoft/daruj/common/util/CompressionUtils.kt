package net.jsoft.daruj.common.util

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.compressToByteArray(quality: Int = 50): ByteArray {
    val compressed = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, quality, compressed)

    return compressed.toByteArray()
}