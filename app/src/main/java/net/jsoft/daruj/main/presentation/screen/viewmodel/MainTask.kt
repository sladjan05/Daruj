package net.jsoft.daruj.main.presentation.screen.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed class MainTask {
    class ShowError(val uiText: UiText) : MainTask()
}