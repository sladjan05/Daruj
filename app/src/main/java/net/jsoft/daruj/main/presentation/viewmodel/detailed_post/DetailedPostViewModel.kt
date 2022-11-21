package net.jsoft.daruj.main.presentation.viewmodel.detailed_post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.model.canDonateBlood
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.main.domain.model.Post
import net.jsoft.daruj.main.domain.usecase.DeletePostUseCase
import net.jsoft.daruj.main.domain.usecase.GetPostUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.screen.Screen
import javax.inject.Inject

@HiltViewModel
class DetailedPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getPost: GetPostUseCase,
    private val setPostSaved: SetPostSavedUseCase,
    private val deletePost: DeletePostUseCase,

    private val getLocalUser: GetLocalUserUseCase
) : LoadingViewModel<DetailedPostEvent, DetailedPostTask>() {

    var post by mutableStateOf<Post?>(null)
        private set

    var canDonateBlood by mutableStateOf(false)
        private set

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += DetailedPostTask.ShowError(e.uiText) }

        val postId = savedStateHandle.get<String>(Screen.DetailedPost.POST_ID)!!
        viewModelScope.loadSafely { post = getPost(postId) }

        viewModelScope.loadSafely {
            val localUser = getLocalUser()
            canDonateBlood = localUser.canDonateBlood
        }
    }

    override fun onEvent(event: DetailedPostEvent) {
        when (event) {
            is DetailedPostEvent.SaveClick -> viewModelScope.loadSafely("SAVE_POST_${post!!.data.id}") {
                val data = post!!.data
                val newData = data.copy(isSaved = !data.isSaved)

                post = post!!.copy(data = newData)
                setPostSaved(
                    postId = post!!.data.id,
                    saved = newData.isSaved
                )
            }

            is DetailedPostEvent.DeleteClick -> {
                val postId = post!!.data.id

                viewModelScope.loadSafely("DELETE_POST_$postId") {
                    deletePost(postId)
                    mTaskFlow += DetailedPostTask.Finish
                }
            }
        }
    }
}