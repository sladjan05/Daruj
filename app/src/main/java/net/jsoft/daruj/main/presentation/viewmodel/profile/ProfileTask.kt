package net.jsoft.daruj.main.presentation.viewmodel.profile

import net.jsoft.daruj.common.misc.UiText

sealed interface ProfileTask {
    data class ShowError(val message: UiText) : ProfileTask
}