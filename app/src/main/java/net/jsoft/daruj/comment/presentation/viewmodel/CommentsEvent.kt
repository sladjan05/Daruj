package net.jsoft.daruj.comment.presentation.viewmodel

sealed interface CommentsEvent {
    object Refresh : CommentsEvent

    data class CommentChange(val comment: String) : CommentsEvent
    object PostCommentClick : CommentsEvent
}