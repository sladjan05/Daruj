package net.jsoft.daruj.main.presentation.viewmodel.profile.donations

sealed interface DonationsEvent {
    object Refresh : DonationsEvent
    object ReachedEnd : DonationsEvent
}