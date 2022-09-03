package net.jsoft.daruj.common.data.source.remote.dto

import android.net.Uri
import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import net.jsoft.daruj.common.domain.model.LocalUser
import net.jsoft.daruj.common.domain.model.Sex

@Keep
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
    var savedPosts: List<String>? = null,
    var points: Int? = null
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
            isPrivate = isPrivate!!
        ),
        immutable = LocalUser.Immutable(
            displayName = displayName!!,
            savedPosts = savedPosts!!,
            points = points!!
        ),
        data = LocalUser.Data(
            id = id!!,
            pictureUri = pictureUri
        )
    )
}