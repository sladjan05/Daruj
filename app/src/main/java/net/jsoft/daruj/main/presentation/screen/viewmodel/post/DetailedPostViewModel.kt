package net.jsoft.daruj.main.presentation.screen.viewmodel.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.usecase.GetPostUseCase
import net.jsoft.daruj.main.presentation.screen.Screen
import javax.inject.Inject

@HiltViewModel
class DetailedPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getPost: GetPostUseCase
) : LoadingViewModel<Nothing, Nothing>() {

    var post by mutableStateOf<Post?>(null)
        private set

    init {
        val postId = savedStateHandle.get<String>(Screen.DetailedPost.POST_ID)!!

        viewModelScope.loadSafely {
            post = getPost(postId)
        }
    }

    override fun onEvent(event: Nothing) = Unit
}