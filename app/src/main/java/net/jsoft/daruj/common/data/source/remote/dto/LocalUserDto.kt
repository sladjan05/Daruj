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
    var sex: Sex? = null,
    var blood: BloodDto? = null,
    var isPrivate: Boolean? = null,
    val savedPosts: List<String> = emptyList(),
    var donations: List<DonationRecordDto> = emptyList(),
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
            isPrivate = isPrivate!!
        ),
        immutable = LocalUser.Immutable(
            savedPosts = savedPosts,
            donations = donations.map(DonationRecordDto::getModel),
            points = points!!
        ),
        data = LocalUser.Data(
            id = id!!,
            pictureUri = pictureUri
        )
    )
}