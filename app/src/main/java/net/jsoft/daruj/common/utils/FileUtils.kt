package net.jsoft.daruj.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.Px
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest

private const val DEFAULT_SIZE = -1

suspend fun Context.getBitmap(
    uri: Uri,
    @Px width: Int = DEFAULT_SIZE,
    @Px height: Int = DEFAULT_SIZE
): Bitmap? {
    val imageLoader = ImageLoader(this)
    val imageRequest = ImageRequest.Builder(this)
        .data(uri)
        .build()

    val drawable = imageLoader.execute(imageRequest).drawable
    return drawable?.toBitmap(
        width = if (width == DEFAULT_SIZE) drawable.intrinsicWidth else width,
        height = if (width == DEFAULT_SIZE) drawable.intrinsicHeight else height
    )
}