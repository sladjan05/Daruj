package net.jsoft.daruj.main.presentation.viewmodel.receipt

import net.jsoft.daruj.main.domain.model.Receipt

sealed interface ReceiptsEvent {
    object Refresh : ReceiptsEvent

    data class ApproveClick(val receipt: Receipt) : ReceiptsEvent
    data class DenyClick(val receipt: Receipt) : ReceiptsEvent
}