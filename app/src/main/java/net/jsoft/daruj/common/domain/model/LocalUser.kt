package net.jsoft.daruj.common.domain.model

import android.net.Uri

data class LocalUser(
    val mutable: Mutable,
    val immutable: Immutable,
    val data: Data
) {
    data class Mutable(
        val name: String,
        val surname: String,
        val sex: Sex,
        val blood: Blood,
        val legalId: String?,
        val isPrivate: Boolean,
        val savedPosts: List<String>
    )

    data class Immutable(
        val displayName: String
    )

    data class Data(
        val id: String,
        val pictureUri: Uri?
    )
}