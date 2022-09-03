package net.jsoft.daruj.main.presentation.screen.viewmodel.posts

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import net.jsoft.daruj.main.domain.model.Post

open class PostsViewModel(
    private val getPosts: suspend (reset: Boolean) -> List<Post>,
    private val setPostSaved: suspend (postId: String, saved: Boolean) -> Unit
) : LoadingViewModel<PostsEvent, PostsTask>() {

    protected val mPosts = mutableStateListOf<Post>()
    val posts = mPosts as List<Post>

    private var postsJob: Job? = null

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += PostsTask.ShowError(e.uiText)
        }
    }

    override fun onEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.Refresh -> loadPosts(true)

            is PostsEvent.SaveClick -> viewModelScope.loadSafely("SAVE_POST_${event.post.data.id}") {
                val post = event.post
                val data = post.data
                val saved = !post.data.isSaved

                mPosts[mPosts.indexOf(post)] = post.copy(
                    data = data.copy(isSaved = saved)
                )

                setPostSaved(post.data.id, saved)
            }

            is PostsEvent.ReachedEnd -> loadPosts()
        }
    }

    private fun loadPosts(reset: Boolean = false) {
        if (postsJob != null) return
        if (reset) mPosts.clear()

        viewModelScope.loadSafely {
            mPosts.addAll(getPosts(reset))
            postsJob = null
        }
    }
}