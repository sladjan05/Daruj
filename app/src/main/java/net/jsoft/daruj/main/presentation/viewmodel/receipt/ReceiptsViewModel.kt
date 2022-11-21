package net.jsoft.daruj.main.presentation.viewmodel.receipt

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.R
import net.jsoft.daruj.common.misc.asUiText
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.plusAssign
import net.jsoft.daruj.common.util.uiText
import net.jsoft.daruj.donate_blood.domain.usecase.ApproveReceiptUseCase
import net.jsoft.daruj.donate_blood.domain.usecase.DenyReceiptUseCase
import net.jsoft.daruj.donate_blood.domain.usecase.GetReceiptsUseCase
import net.jsoft.daruj.main.domain.model.Receipt
import net.jsoft.daruj.main.presentation.screen.Screen
import javax.inject.Inject

@HiltViewModel
class ReceiptsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getReceipts: GetReceiptsUseCase,
    private val approveReceipt: ApproveReceiptUseCase,
    private val denyReceipt: DenyReceiptUseCase
) : LoadingViewModel<ReceiptsEvent, ReceiptsTask>() {

    private val mReceipts = mutableStateListOf<Receipt>()
    val receipts = mReceipts as List<Receipt>

    private val postId = savedStateHandle.get<String>(Screen.Receipts.POST_ID)!!

    init {
        viewModelScope.registerExceptionHandler { e -> mTaskFlow += ReceiptsTask.ShowError(e.uiText) }
        getReceipts()
    }

    override fun onEvent(event: ReceiptsEvent) {
        when (event) {
            is ReceiptsEvent.Refresh -> getReceipts()

            is ReceiptsEvent.ApproveClick -> {
                val userId = event.receipt.user.id

                viewModelScope.loadSafely("APPROVE_RECEIPT_$userId") {
                    approveReceipt(postId, userId)

                    mReceipts.remove(event.receipt)
                    mTaskFlow += ReceiptsTask.ShowInfo(R.string.tx_receipt_marked_valid.asUiText())
                }
            }

            is ReceiptsEvent.DenyClick -> {
                val userId = event.receipt.user.id

                viewModelScope.loadSafely("DENY_RECEIPT_$userId") {
                    denyReceipt(postId, userId)

                    mReceipts.remove(event.receipt)
                    mTaskFlow += ReceiptsTask.ShowInfo(R.string.tx_receipt_marked_invalid.asUiText())
                }
            }
        }
    }

    private fun getReceipts() {
        mReceipts.clear()
        viewModelScope.loadSafely("LOAD_RECEIPTS") { mReceipts += getReceipts(postId) }
    }
}