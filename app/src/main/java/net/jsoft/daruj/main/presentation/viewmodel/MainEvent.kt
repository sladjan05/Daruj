package net.jsoft.daruj.main.presentation.viewmodel

sealed interface MainEvent {
    data class PageChange(val page: Int) : MainEvent
}