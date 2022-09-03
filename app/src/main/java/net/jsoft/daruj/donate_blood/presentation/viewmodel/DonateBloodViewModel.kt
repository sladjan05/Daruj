package net.jsoft.daruj.donate_blood.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.usecase.GetPostUseCase
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.utils.plusAssign
import net.jsoft.daruj.common.utils.uiText
import net.jsoft.daruj.donate_blood.presentation.DonateBloodActivity
import net.jsoft.daruj.main.domain.model.Post
import javax.inject.Inject

@HiltViewModel
class DonateBloodViewModel @Inject constructor(
    //savedStateHandle: SavedStateHandle,

    //private val getPost: GetPostUseCase
) : LoadingViewModel<Nothing, DonateBloodTask>() {

    var post by mutableStateOf<Post?>(null)
        private set

    init {
        viewModelScope.registerExceptionHandler { e ->
            mTaskFlow += DonateBloodTask.ShowError(e.uiText)
        }

        //val postId = savedStateHandle.get<String>(DonateBloodActivity.POST_ID)!!
        //viewModelScope.loadSafely { post = getPost(postId) }
    }

    override fun onEvent(event: Nothing) = Unit
}