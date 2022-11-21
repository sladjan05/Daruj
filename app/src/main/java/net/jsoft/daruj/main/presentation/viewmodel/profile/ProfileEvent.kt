package net.jsoft.daruj.main.presentation.viewmodel.profile

sealed interface ProfileEvent {
    object Refresh : ProfileEvent
    data class PageChange(val page: Int) : ProfileEvent
}