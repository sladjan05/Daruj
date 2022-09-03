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
    val id: String? = null,

    val userId: String? = null,
    val timestamp: Timestamp? = null,


    val name: String? = null,
    val surname: String? = null,
    val parentName: String? = null,

    val location: String? = null,
    val donorsRequired: Int? = null,
    val blood: BloodDto? = null,
    val description: String? = null,

    val commentCount: Int? = null,
    val shareCount: Int? = null,
    val donorCount: Int? = null

) {
    fun getModel(
        user: User,
        pictureUri: Uri?,
        isSaved: Boolean
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
            commentCount = commentCount!!,
            shareCount = shareCount!!,
            donorCount = donorCount!!
        ),
        data = Post.Data(
            id = id!!,
            pictureUri = pictureUri,
            isSaved = isSaved
        )
    )
}