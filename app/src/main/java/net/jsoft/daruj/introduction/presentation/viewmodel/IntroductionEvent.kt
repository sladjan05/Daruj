package net.jsoft.daruj.introduction.presentation.viewmodel

sealed interface IntroductionEvent {
    class PageChange(val page: Int) : IntroductionEvent
}