package net.jsoft.daruj.comment.domain.model

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.parcelize.Parcelize
import net.jsoft.daruj.common.domain.model.User
import java.time.ZonedDateTime

@Keep
@Parcelize
data class Comment(
    val mutable: Mutable,
    val immutable: Immutable,
    val data: Data
) : Parcelable {

    @Keep
    @Parcelize
    data class Mutable(
        val content: String
    ) : Parcelable

    @Keep
    @Parcelize
    data class Immutable(
        val user: User,
        val timestamp: ZonedDateTime
    ) : Parcelable

    @Keep
    @Parcelize
    data class Data(
        val id: String
    ) : Parcelable
}