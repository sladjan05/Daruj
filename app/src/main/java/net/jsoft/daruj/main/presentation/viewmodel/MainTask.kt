package net.jsoft.daruj.main.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface MainTask {
    data class ShowError(val message: UiText) : MainTask
}