package net.jsoft.daruj.common.data.source.remote.dto

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex

class LocalUserDto(
    @DocumentId
    val id: String? = null,

    var name: String? = null,
    var surname: String? = null,
    var displayName: String? = null,
    var sex: Sex? = null,
    var blood: BloodDto? = null,
    var legalId: String? = null,
    var isPrivate: Boolean? = null,
    var savedPosts: List<String>? = null
) {
    fun getModel(
        pictureUri: Uri?
    ) = LocalUser(
        mutable = LocalUser.Mutable(
            name = name!!,
            surname = surname!!,
            sex = sex!!,
            blood = blood!!.getModel(),
            legalId = legalId,
            isPrivate = isPrivate!!,
            savedPosts = savedPosts!!
        ),
        immutable = LocalUser.Immutable(
            displayName = displayName!!
        ),
        data = LocalUser.Data(
            id = id!!,
            pictureUri = pictureUri
        )
    )
}