package net.jsoft.daruj.main.presentation.screen.viewmodel.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.common.domain.usecase.GetPostUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.screen.Screen
import javax.inject.Inject

@HiltViewModel
class DetailedPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getPost: GetPostUseCase,
    private val getLocalUser: GetLocalUserUseCase,
    private val setPostSaved: SetPostSavedUseCase
) : LoadingViewModel<DetailedPostEvent, Nothing>() {

    var post by mutableStateOf<Post?>(null)
        private set

    var isSaved by mutableStateOf(false)
        private set

    init {
        val postId = savedStateHandle.get<String>(Screen.DetailedPost.POST_ID)!!

        viewModelScope.loadSafely {
            val deferredPost = async { getPost(postId) }
            val deferredUser = async { getLocalUser() }

            post = deferredPost.await()

            val user = deferredUser.await()
            val savedPosts = user.immutable.savedPosts

            isSaved = savedPosts.contains(post!!.data.id)
        }
    }

    override fun onEvent(event: DetailedPostEvent) {
        when (event) {
            is DetailedPostEvent.SaveClick -> viewModelScope.loadSafely {
                isSaved = !isSaved

                setPostSaved(
                    postId = post!!.data.id,
                    saved = isSaved
                )
            }
        }
    }
}