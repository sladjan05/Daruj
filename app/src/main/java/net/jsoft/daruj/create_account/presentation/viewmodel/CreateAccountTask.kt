package net.jsoft.daruj.create_account.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface CreateAccountTask {
    object CreateAccountClick : CreateAccountTask

    class ShowError(val message: UiText) : CreateAccountTask
    class ShowInfo(val message: UiText) : CreateAccountTask
}