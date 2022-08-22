package net.jsoft.daruj.common.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.compressToByteArray(quality: Int): ByteArray {
    val compressed = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, quality, compressed)

    return compressed.toByteArray()
}