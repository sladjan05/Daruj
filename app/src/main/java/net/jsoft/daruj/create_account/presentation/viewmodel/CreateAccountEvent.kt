package net.jsoft.daruj.create_account.presentation.viewmodel

import android.net.Uri
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.Sex

sealed interface CreateAccountEvent {
    object Dismiss : CreateAccountEvent

    data class PictureChange(val uri: Uri) : CreateAccountEvent

    data class NameChange(val name: String) : CreateAccountEvent
    data class SurnameChange(val surname: String) : CreateAccountEvent

    object SexClick : CreateAccountEvent
    data class SexChange(val sex: Sex) : CreateAccountEvent

    object BloodClick : CreateAccountEvent
    data class BloodChange(val blood: Blood) : CreateAccountEvent

    object IsPrivateClick : CreateAccountEvent

    object Finish : CreateAccountEvent
}