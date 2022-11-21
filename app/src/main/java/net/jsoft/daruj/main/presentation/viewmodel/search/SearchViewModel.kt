package net.jsoft.daruj.main.presentation.viewmodel.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import net.jsoft.daruj.common.domain.model.canDonateBlood
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.usecase.DeletePostUseCase
import net.jsoft.daruj.main.domain.usecase.SearchPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsEvent
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsTask
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPosts: SearchPostsUseCase,
    private val getLocalUser: GetLocalUserUseCase,
    private val setPostSaved: SetPostSavedUseCase,
    private val deletePost: DeletePostUseCase
) : LoadingViewModel<PostsEvent, PostsTask>() {

    private val mPosts = mutableStateListOf<Post>()
    val posts = mPosts as List<Post>

    var criteria by mutableStateOf(UiText.Empty)
        private set

    var canDonateBlood by mutableStateOf(false)
        private set

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += PostsTask.ShowError(e.uiText) }

        viewModelScope.loadSafely {
            val localUser = getLocalUser()
            canDonateBlood = localUser.canDonateBlood
        }
    }

    override fun onEvent(event: PostsEvent) {
        when (event) {
            is SearchEvent.SearchChange -> {
                criteria = event.content.asUiText()

                viewModelScope.loadSafely("SEARCH_POSTS") {
                    if (event.content.isEmpty()) return@loadSafely

                    delay(1.seconds)
                    loadPosts()
                }
            }

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
                    loadPosts()
                }
            }
        }
    }

    private fun loadPosts() {
        mPosts.clear()
        viewModelScope.loadSafely("SEARCH_POSTS") { mPosts += searchPosts(criteria.toString()) }
    }
}