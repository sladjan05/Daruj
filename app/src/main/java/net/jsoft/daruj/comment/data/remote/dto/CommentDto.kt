package net.jsoft.daruj.comment.data.remote.dto

import com.google.errorprone.annotations.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.common.domain.model.User
import java.time.ZoneId
import java.time.ZonedDateTime

@Keep
class CommentDto(
    @DocumentId
    val id: String? = null,

    val postId: String? = null,
    val userId: String? = null,
    val content: String? = null,
    val timestamp: Timestamp? = null
) {
    fun getModel(
        user: User
    ) = Comment(
        mutable = Comment.Mutable(
            content = content!!
        ),
        immutable = Comment.Immutable(
            user = user,
            timestamp = ZonedDateTime.ofInstant(
                timestamp!!.toDate().toInstant(),
                ZoneId.of("UTC")
            )
        ),
        data = Comment.Data(
            id = id!!
        )
    )
}