package net.jsoft.daruj.main.presentation.screen.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface MainTask {
    class ShowError(val message: UiText) : MainTask
}