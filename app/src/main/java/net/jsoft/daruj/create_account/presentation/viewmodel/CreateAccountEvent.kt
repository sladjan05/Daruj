package net.jsoft.daruj.create_account.presentation.viewmodel

import android.net.Uri
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.domain.model.Sex

sealed class CreateAccountEvent {
    class PictureChange(val uri: Uri) : CreateAccountEvent()

    class NameChange(val name: String) : CreateAccountEvent()
    class SurnameChange(val surname: String) : CreateAccountEvent()

    object DayClick : CreateAccountEvent()
    object DayDismiss : CreateAccountEvent()
    class DayIndexChange(val index: Int) : CreateAccountEvent()

    object MonthClick : CreateAccountEvent()
    object MonthDismiss : CreateAccountEvent()
    class MonthIndexChange(val index: Int) : CreateAccountEvent()

    object YearClick : CreateAccountEvent()
    object YearDismiss : CreateAccountEvent()
    class YearIndexChange(val index: Int) : CreateAccountEvent()

    object SexClick : CreateAccountEvent()
    class SexChange(val sex: Sex) : CreateAccountEvent()

    object BloodClick : CreateAccountEvent()
    class BloodChange(val blood: Blood) : CreateAccountEvent()

    object LegalIdInfoClick : CreateAccountEvent()
    class LegalIdChange(val legalId: String) : CreateAccountEvent()

    object CreateAccount : CreateAccountEvent()
}