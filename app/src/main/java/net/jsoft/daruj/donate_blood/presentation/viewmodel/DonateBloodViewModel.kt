package net.jsoft.daruj.donate_blood.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.donate_blood.domain.usecase.SendReceiptApprovalRequestUseCase
import net.jsoft.daruj.donate_blood.presentation.DonateBloodActivity
import net.jsoft.daruj.main.domain.model.Post
import javax.inject.Inject

@HiltViewModel
class DonateBloodViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendReceiptApprovalRequest: SendReceiptApprovalRequestUseCase
) : LoadingViewModel<DonateBloodEvent, DonateBloodTask>() {

    val post = savedStateHandle.get<Post>(DonateBloodActivity.Post)!!

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += DonateBloodTask.ShowError(e.uiText) }
    }

    override fun onEvent(event: DonateBloodEvent) {
        when (event) {
            is DonateBloodEvent.PicturePick -> {
                val postId = post.data.id

                viewModelScope.loadSafely("SEND_APPROVAL_$postId") {
                    sendReceiptApprovalRequest(postId, event.uri)
                    mTaskFlow += DonateBloodTask.Sent
                }
            }
        }
    }
}