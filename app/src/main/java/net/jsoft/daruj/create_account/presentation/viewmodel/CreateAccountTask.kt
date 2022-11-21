package net.jsoft.daruj.create_account.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface CreateAccountTask {
    object CreateAccountClick : CreateAccountTask

    data class ShowError(val message: UiText) : CreateAccountTask
}