package net.jsoft.daruj.main.presentation.screen.viewmodel.profile

import net.jsoft.daruj.common.misc.UiText

sealed interface ProfileTask {
    class ShowError(val message: UiText) : ProfileTask
}