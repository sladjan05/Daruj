package net.jsoft.daruj.main.presentation.viewmodel.receipt

import net.jsoft.daruj.common.misc.UiText

sealed interface ReceiptsTask {
    data class ShowError(val message: UiText) : ReceiptsTask
    data class ShowInfo(val message: UiText) : ReceiptsTask
}