package net.jsoft.daruj.main.presentation.screen.viewmodel.saved

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.formatPostTimestamp
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.usecase.GetSavedPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SavePostUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedPosts: GetSavedPostsUseCase,
    private val savePost: SavePostUseCase
) : LoadingViewModel<SavedEvent, SavedTask>() {

    private val _posts = mutableStateListOf<Post>()
    val posts = _posts as List<Post>

    private var postsJob: Job? = null

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += SavedTask.ShowError(e.uiText)
        }

        loadNewPosts()
    }

    override fun onEvent(event: SavedEvent) {
        when (event) {
            is SavedEvent.Refresh -> loadNewPosts()

            is SavedEvent.SaveClick -> viewModelScope.loadSafely("SAVE_POST_${event.post.data.id}") {
                val post = event.post
                _posts[_posts.indexOf(post)] = post.copy(
                    data = post.data.copy(isSaved = !post.data.isSaved)
                )

                savePost(post)
            }

            is SavedEvent.ReachedEnd -> {
                if (postsJob != null) return

                postsJob = viewModelScope.loadSafely {
                    _posts.addAll(getSavedPosts())
                    postsJob = null
                }
            }
        }
    }

    private fun loadNewPosts() {
        _posts.clear()

        viewModelScope.loadSafely("LOAD_POSTS") {
            _posts.addAll(getSavedPosts(true))
        }
    }
}