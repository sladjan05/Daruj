package net.jsoft.daruj.create_account.presentation.viewmodel

import net.jsoft.daruj.common.util.UiText

sealed class CreateAccountTask {
    object Finish : CreateAccountTask()

    class ShowError(val message: UiText) : CreateAccountTask()
    class ShowInfo(val message: UiText) : CreateAccountTask()
}