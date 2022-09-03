package net.jsoft.daruj.donate_blood.presentation.viewmodel

import net.jsoft.daruj.common.misc.UiText

sealed interface DonateBloodTask {
    class ShowError(val message: UiText) : DonateBloodTask
}