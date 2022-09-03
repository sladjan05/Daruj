package net.jsoft.daruj.common.domain.model

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class LocalUser(
    val mutable: Mutable,
    val immutable: Immutable,
    val data: Data
) : Parcelable {

    @Keep
    @Parcelize
    data class Mutable(
        val name: String,
        val surname: String,
        val sex: Sex,
        val blood: Blood,
        val legalId: String?,
        val isPrivate: Boolean
    ) : Parcelable

    @Keep
    @Parcelize
    data class Immutable(
        val displayName: String,
        val savedPosts: List<String>,
        val points: Int
    ) : Parcelable

    @Keep
    @Parcelize
    data class Data(
        val id: String,
        val pictureUri: Uri?
    ) : Parcelable
}