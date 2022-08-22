package net.jsoft.daruj.main.presentation.screen.viewmodel.saved

import net.jsoft.daruj.common.misc.UiText

sealed class SavedTask {
    class ShowError(val message: UiText) : SavedTask()
    class ShowInfo(val message: UiText) : SavedTask()
}