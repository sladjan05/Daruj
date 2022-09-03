package net.jsoft.daruj.main.presentation.screen.viewmodel

sealed interface MainEvent {
    class PageChange(val page: Int) : MainEvent
}