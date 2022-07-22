package net.jsoft.daruj.common.domain.model

import android.net.Uri

open class User(
    val id: String,
    val pictureUri: Uri?,
    val displayName: String,
    val sex: Sex,
    val blood: Blood
)