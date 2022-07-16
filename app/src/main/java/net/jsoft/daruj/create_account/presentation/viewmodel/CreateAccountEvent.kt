package net.jsoft.daruj.create_account.presentation.viewmodel

import net.jsoft.daruj.common.domain.Blood
import net.jsoft.daruj.common.domain.Sex

sealed class CreateAccountEvent {
    class NameChange(val name: String) : CreateAccountEvent()
    class SurnameChange(val surname: String) : CreateAccountEvent()

    object DayClick : CreateAccountEvent()
    class DayIndexChange(val index: Int) : CreateAccountEvent()

    object MonthClick : CreateAccountEvent()
    class MonthIndexChange(val index: Int) : CreateAccountEvent()

    object YearClick : CreateAccountEvent()
    class YearIndexChange(val index: Int) : CreateAccountEvent()

    object SexClick : CreateAccountEvent()
    class SexChange(val sex: Sex) : CreateAccountEvent()

    object BloodClick : CreateAccountEvent()
    class BloodChange(val blood: Blood) : CreateAccountEvent()

    object LegalIdInfoClick : CreateAccountEvent()
    class LegalIdChange(val legalId: String) : CreateAccountEvent()

    object CreateAccount : CreateAccountEvent()
}