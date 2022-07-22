package net.jsoft.daruj.common.domain.model

import android.net.Uri

class LocalUser(
    id: String,
    pictureUri: Uri?,
    val name: String,
    val surname: String,
    displayName: String,
    sex: Sex,
    blood: Blood,
    val legalId: String?,
    val isPrivate: Boolean
) : User(
    id = id,
    pictureUri = pictureUri,
    displayName = displayName,
    sex = sex,
    blood = blood
) {
    class Constructable(
        val name: String,
        val surname: String,
        val sex: Sex,
        val blood: Blood,
        val legalId: String?,
        val isPrivate: Boolean
    )
}