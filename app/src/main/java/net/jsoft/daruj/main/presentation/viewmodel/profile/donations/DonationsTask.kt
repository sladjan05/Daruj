package net.jsoft.daruj.main.presentation.viewmodel.profile.donations

import net.jsoft.daruj.common.misc.UiText

sealed interface DonationsTask {
    data class ShowError(val message: UiText) : DonationsTask
}