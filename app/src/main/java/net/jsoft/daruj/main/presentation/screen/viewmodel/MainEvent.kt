package net.jsoft.daruj.main.presentation.screen.viewmodel

sealed class MainEvent {
    class PageChange(val page: Int) : MainEvent()
}