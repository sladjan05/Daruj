package net.jsoft.daruj.main.presentation.viewmodel.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import net.jsoft.daruj.common.domain.model.canDonateBlood
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.main.domain.model.Post

open class PostsViewModel(
    private val getPosts: suspend (reset: Boolean) -> List<Post>,
    private val setPostSaved: suspend (postId: String, isSaved: Boolean) -> Unit,
    private val deletePost: suspend (postId: String) -> Unit,

    private val getLocalUser: GetLocalUserUseCase
) : LoadingViewModel<PostsEvent, PostsTask>() {

    private val mPosts = mutableStateListOf<Post>()
    val posts = mPosts as List<Post>

    var canDonateBlood by mutableStateOf(false)
        private set

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += PostsTask.ShowError(e.uiText) }
    }

    override fun onEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.Refresh -> loadPosts(true)

            is PostsEvent.SaveClick -> viewModelScope.loadSafely("SAVE_POST_${event.post.data.id}") {
                val post = event.post
                val data = post.data
                val isSaved = !post.data.isSaved

                val newData = data.copy(isSaved = isSaved)
                mPosts[mPosts.indexOf(post)] = post.copy(data = newData)

                setPostSaved(post.data.id, isSaved)
            }

            is PostsEvent.DeleteClick -> {
                val postId = event.post.data.id
                viewModelScope.loadSafely("DELETE_POST_$postId") {
                    deletePost(postId)
                    loadPosts(true)
                }
            }

            is PostsEvent.ReachedEnd -> loadPosts()
        }
    }

    private fun loadPosts(reset: Boolean = mPosts.isEmpty()) {
        if (reset) {
            mPosts.clear()
            viewModelScope.loadSafely("LOAD_LOCAL_USER") {
                val localUser = getLocalUser()
                canDonateBlood = localUser.canDonateBlood
            }
        }
        viewModelScope.loadSafely("LOAD_POSTS") { mPosts += getPosts(reset) }
    }
}