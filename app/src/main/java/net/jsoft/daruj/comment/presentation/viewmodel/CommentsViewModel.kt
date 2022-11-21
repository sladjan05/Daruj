package net.jsoft.daruj.comment.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import net.jsoft.daruj.comment.domain.model.Comment
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.main.domain.usecase.GetCommentsUseCase
import net.jsoft.daruj.main.domain.usecase.PostCommentUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getComments: GetCommentsUseCase,
    private val postComment: PostCommentUseCase
) : LoadingViewModel<CommentsEvent, CommentsTask>() {

    private val mComments = mutableStateListOf<Comment>()
    val comments = mComments as List<Comment>

    var comment by mutableStateOf(UiText.Empty)
        private set

    private val postId = savedStateHandle.get<String>(PostID)!!

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += CommentsTask.ShowError(e.uiText) }
        refreshComments()
    }

    override fun onEvent(event: CommentsEvent) {
        when (event) {
            is CommentsEvent.Refresh -> refreshComments()

            is CommentsEvent.CommentChange -> comment = event.comment.asUiText()

            is CommentsEvent.PostCommentClick -> {
                val comment = Comment.Mutable(content = comment.toString())
                this.comment = UiText.Empty

                viewModelScope.loadSafely {
                    postComment(postId, comment)

                    delay(1.seconds)
                    refreshComments()
                }
            }
        }
    }

    private fun refreshComments() {
        mComments.clear()
        viewModelScope.loadSafely("LOAD_COMMENTS") { mComments += getComments(postId) }
    }

    companion object {
        const val PostID = "postId"
    }
}