package net.jsoft.daruj.main.data.source.remote.dto

import android.net.Uri
import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import net.jsoft.daruj.common.data.source.remote.dto.BloodDto
import net.jsoft.daruj.common.domain.model.User
import net.jsoft.daruj.main.domain.model.Post
import java.time.ZoneId
import java.time.ZonedDateTime

@Keep
class PostDto(
    @DocumentId
    var id: String? = null,

    var userId: String? = null,
    var timestamp: Timestamp? = null,

    var name: String? = null,
    var surname: String? = null,
    var parentName: String? = null,

    var searchCriteria: String? = null,

    var location: String? = null,
    var donorsRequired: Int? = null,
    var blood: BloodDto? = null,
    var description: String? = null,

    var shareCount: Int? = null,
    var donorCount: Int? = null
) {
    fun getModel(
        user: User,
        pictureUri: Uri?,
        isMyPost: Boolean,
        receiptCount: Int,
        isSaved: Boolean,
        isBloodCompatible: Boolean,
        commentCount: Int
    ) = Post(
        mutable = Post.Mutable(
            name = name!!,
            surname = surname!!,
            parentName = parentName!!,
            location = location!!,
            blood = blood!!.getModel(),
            donorsRequired = donorsRequired!!,
            description = description!!
        ),
        immutable = Post.Immutable(
            user = user,
            timestamp = ZonedDateTime.ofInstant(
                timestamp!!.toDate().toInstant(),
                ZoneId.of("UTC")
            ),
            commentCount = commentCount,
            shareCount = shareCount!!,
            donorCount = donorCount!!
        ),
        data = Post.Data(
            id = id!!,
            pictureUri = pictureUri,
            isMyPost = isMyPost,
            receiptCount = receiptCount,
            isSaved = isSaved,
            isBloodCompatible = isBloodCompatible
        )
    )
}