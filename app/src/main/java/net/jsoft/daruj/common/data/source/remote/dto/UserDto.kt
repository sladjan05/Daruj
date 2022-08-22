package net.jsoft.daruj.common.data.source.remote.dto

import android.net.Uri
import net.jsoft.daruj.common.domain.model.Sex
import net.jsoft.daruj.common.domain.model.User

data class UserDto(
    var id: String? = null,
    var displayName: String? = null,
    var sex: Sex? = null,
    var blood: BloodDto? = null
) {
    fun getModel(
        pictureUri: Uri?
    ) = User(
        id = id!!,
        pictureUri = pictureUri,
        displayName = displayName!!,
        sex = sex!!,
        blood = blood!!.getModel()
    )
}