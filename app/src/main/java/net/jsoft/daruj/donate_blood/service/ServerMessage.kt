package net.jsoft.daruj.donate_blood.service

sealed interface ServerMessage {
    companion object {
        fun fromData(data: Map<String, String>): ServerMessage =
            when (val type = data["type"]) {
                "PENDING_RECEIPT" -> PendingReceipt(
                    postId = data["postId"]!!,
                    userId = data["userId"]!!
                )

                "RECEIPT_APPROVAL" -> ReceiptApproval(
                    postId = data["postId"]!!,
                    isApproved = data["isApproved"].toBoolean()
                )

                "NEW_COMMENT" -> NewComment(
                    commentId = data["commentId"]!!,
                    postId = data["postId"]!!
                )

                else -> throw InvalidDataTypeException(type!!)
            }
    }

    data class PendingReceipt(
        val postId: String,
        val userId: String
    ) : ServerMessage

    data class ReceiptApproval(
        val postId: String,
        val isApproved: Boolean
    ) : ServerMessage

    data class NewComment(
        val commentId: String,
        val postId: String
    ) : ServerMessage
}