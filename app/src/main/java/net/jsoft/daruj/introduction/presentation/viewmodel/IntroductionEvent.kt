package net.jsoft.daruj.introduction.presentation.viewmodel

sealed interface IntroductionEvent {
    data class PageChange(val page: Int) : IntroductionEvent
}