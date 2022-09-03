package net.jsoft.daruj.main.domain.model

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.User
import java.time.ZonedDateTime

@Keep
@Parcelize
data class Post(
    val mutable: Mutable,
    val immutable: Immutable,
    val data: Data
) : Parcelable {

    @Keep
    @Parcelize
    data class Mutable(
        val name: String,
        val surname: String,
        val parentName: String,
        val location: String,
        val blood: Blood,
        val donorsRequired: Int,
        val description: String
    ) : Parcelable {
        val fullName: String
            get() = "$name $surname"
    }

    @Keep
    @Parcelize
    data class Immutable(
        val user: User,
        val timestamp: ZonedDateTime,
        val commentCount: Int,
        val shareCount: Int,
        val donorCount: Int
    ) : Parcelable

    @Keep
    @Parcelize
    data class Data(
        val id: String,
        val pictureUri: Uri?,
        val isSaved: Boolean
    ) : Parcelable
}