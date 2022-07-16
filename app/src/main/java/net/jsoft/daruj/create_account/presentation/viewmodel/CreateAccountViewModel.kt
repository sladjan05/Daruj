package net.jsoft.daruj.create_account.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import net.jsoft.daruj.R
import net.jsoft.daruj.common.domain.Blood
import net.jsoft.daruj.common.domain.Sex
import net.jsoft.daruj.common.presentation.viewmodel.LoadingViewModel
import net.jsoft.daruj.common.util.UiText
import net.jsoft.daruj.common.util.asUiText
import java.time.LocalDate

class CreateAccountViewModel : LoadingViewModel<CreateAccountEvent, Nothing>() {

    var name by mutableStateOf("".asUiText())
        private set

    var surname by mutableStateOf("".asUiText())
        private set

    var birth: LocalDate by mutableStateOf(LocalDate.now())
        private set

    var dayExpanded by mutableStateOf(false)
        private set

    var monthExpanded by mutableStateOf(false)
        private set

    var yearExpanded by mutableStateOf(false)
        private set

    var sex by mutableStateOf(Sex.MALE)
        private set

    var sexExpanded by mutableStateOf(false)
        private set

    var blood by mutableStateOf(
        Blood(
            type = Blood.Type.A,
            rh = Blood.RH.POSITIVE
        )
    )
        private set

    var bloodExpanded by mutableStateOf(false)
        private set

    var legalId by mutableStateOf("".asUiText())
        private set

    var isLegalIdInfoExpanded by mutableStateOf(false)
        private set

    val days: List<UiText>
        get() = (1..birth.month.length(birth.isLeapYear)).toList().map { day ->
            day.toString().asUiText()
        }

    override fun onEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.NameChange -> name = event.name.asUiText()
            is CreateAccountEvent.SurnameChange -> surname = event.surname.asUiText()

            is CreateAccountEvent.DayClick -> setExpanded(day = !dayExpanded)
            is CreateAccountEvent.DayIndexChange -> {
                setBirth(day = event.index + 1)
                setExpanded()
            }

            is CreateAccountEvent.MonthClick -> setExpanded(month = !monthExpanded)
            is CreateAccountEvent.MonthIndexChange -> {
                setBirth(month = event.index + 1)
                setExpanded()
            }

            is CreateAccountEvent.YearClick -> setExpanded(year = !yearExpanded)
            is CreateAccountEvent.YearIndexChange -> {
                setBirth(year = LocalDate.now().year - 18 - event.index)
                setExpanded()
            }

            is CreateAccountEvent.SexClick -> setExpanded(sex = !sexExpanded)

            is CreateAccountEvent.SexChange -> {
                sex = event.sex
                setExpanded()
            }

            is CreateAccountEvent.BloodClick -> setExpanded(blood = !bloodExpanded)

            is CreateAccountEvent.BloodChange -> {
                blood = event.blood
                setExpanded()
            }

            is CreateAccountEvent.LegalIdChange -> legalId = event.legalId.asUiText()

            is CreateAccountEvent.LegalIdInfoClick -> isLegalIdInfoExpanded = !isLegalIdInfoExpanded
            is CreateAccountEvent.CreateAccount -> TODO()
        }
    }

    private fun setExpanded(
        day: Boolean = false,
        month: Boolean = false,
        year: Boolean = false,
        sex: Boolean = false,
        blood: Boolean = false
    ) {
        dayExpanded = day
        monthExpanded = month
        yearExpanded = year
        sexExpanded = sex
        bloodExpanded = blood
    }

    private fun setBirth(
        day: Int = birth.dayOfMonth,
        month: Int = birth.month.ordinal + 1,
        year: Int = birth.year
    ) {
        birth = LocalDate.of(year, month, day)
    }

    companion object {
        val years: List<UiText>
            get() = ((LocalDate.now().year - 18) downTo 1900).toList().map { year ->
                year.toString().asUiText()
            }
    }
}