package net.jsoft.daruj.main.presentation.screen.viewmodel.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.formatPostTimestamp
import net.jsoft.daruj.common.utils.formatSMSWaitTime
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.usecase.GetRecommendedPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SavePostUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNextPosts: GetRecommendedPostsUseCase,
    private val savePost: SavePostUseCase
) : LoadingViewModel<HomeEvent, HomeTask>() {

    private val _posts = mutableStateListOf<Post>()
    val posts = _posts as List<Post>

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += HomeTask.ShowError(e.uiText)
        }

        loadNewPosts()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> loadNewPosts()

            is HomeEvent.SaveClick -> viewModelScope.loadSafely("SAVE_POST_${event.post.data.id}") {
                val post = event.post
                _posts[_posts.indexOf(post)] = post.copy(
                    data = post.data.copy(
                        isSaved = !post.data.isSaved
                    )
                )

                savePost(post)
            }
        }
    }

    private fun loadNewPosts() {
        _posts.clear()

        viewModelScope.loadSafely("LOAD_POSTS") {
            _posts.addAll(getNextPosts(true))
        }
    }
}