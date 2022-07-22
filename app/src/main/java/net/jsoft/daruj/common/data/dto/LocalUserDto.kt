package net.jsoft.daruj.common.data.dto

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
    var isPrivate: Boolean? = null
) {
    fun getModel(
        pictureUri: Uri?
    ) = LocalUser(
        id!!,
        pictureUri,
        name!!,
        surname!!,
        displayName!!,
        sex!!,
        blood!!.getModel(),
        legalId!!,
        isPrivate!!
    )
}