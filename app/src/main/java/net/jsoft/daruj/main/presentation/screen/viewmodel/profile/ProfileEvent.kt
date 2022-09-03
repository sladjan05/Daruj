package net.jsoft.daruj.main.presentation.screen.viewmodel.profile

sealed interface ProfileEvent {
    class PageChange(val page: Int) : ProfileEvent
}