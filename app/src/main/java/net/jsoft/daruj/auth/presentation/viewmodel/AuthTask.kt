package net.jsoft.daruj.auth.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface AuthTask {
    data class ShowError(val message: UiText) : AuthTask
}