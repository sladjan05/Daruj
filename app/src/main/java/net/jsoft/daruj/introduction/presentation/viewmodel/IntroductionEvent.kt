package net.jsoft.daruj.introduction.presentation.viewmodel

sealed class IntroductionEvent {
    class PageSwitch(val page: Int) : IntroductionEvent()
    object Next : IntroductionEvent()
}