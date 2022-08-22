package net.jsoft.daruj.create_account.presentation.viewmodel

import android.net.Uri
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.Sex

sealed class CreateAccountEvent {
    object Dismiss : CreateAccountEvent()

    class PictureChange(val uri: Uri) : CreateAccountEvent()

    class NameChange(val name: String) : CreateAccountEvent()
    class SurnameChange(val surname: String) : CreateAccountEvent()

    object SexClick : CreateAccountEvent()
    class SexChange(val sex: Sex) : CreateAccountEvent()

    object BloodClick : CreateAccountEvent()
    class BloodChange(val blood: Blood) : CreateAccountEvent()

    object LegalIdInfoClick : CreateAccountEvent()
    class LegalIdChange(val legalId: String) : CreateAccountEvent()

    object CreateAccount : CreateAccountEvent()
}