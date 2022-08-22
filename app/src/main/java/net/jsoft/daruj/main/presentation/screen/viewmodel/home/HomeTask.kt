package net.jsoft.daruj.main.presentation.screen.viewmodel.home

import net.jsoft.daruj.common.misc.UiText

sealed class HomeTask {
    class ShowError(val uiText: UiText) : HomeTask()
}
