package net.jsoft.daruj.common.domain.model

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class User(
    val id: String,
    val pictureUri: Uri?,
    val displayName: String,
    val sex: Sex,
    val blood: Blood,
    val points: Int
) : Parcelable