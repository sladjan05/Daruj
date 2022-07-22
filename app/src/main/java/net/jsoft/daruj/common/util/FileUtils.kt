package net.jsoft.daruj.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun Uri.getBitmap(context: Context): Bitmap = if (Build.VERSION.SDK_INT < 28) {
    MediaStore.Images.Media.getBitmap(context.contentResolver, this)
} else {
    val source = ImageDecoder.createSource(context.contentResolver, this)
    ImageDecoder.decodeBitmap(source)
}