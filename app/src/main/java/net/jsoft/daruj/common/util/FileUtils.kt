package net.jsoft.daruj.common.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.Px
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import java.io.File
import java.util.UUID

private const val KEEP = -1

suspend fun Context.getBitmap(
    uri: Uri,
    @Px width: Int = KEEP,
    @Px height: Int = KEEP
): Bitmap? {
    val imageLoader = ImageLoader(this)
    val imageRequest = ImageRequest.Builder(this)
        .data(uri)
        .build()

    val drawable = imageLoader.execute(imageRequest).drawable

    return drawable?.toBitmap(
        width = if (width == KEEP) drawable.intrinsicWidth else width,
        height = if (width == KEEP) drawable.intrinsicHeight else height
    )
}

fun Context.createCacheFile(name: String) = File(cacheDir, name)
fun Context.createUniqueCacheFile(extension: String) = createCacheFile("${UUID.randomUUID()}.$extension")