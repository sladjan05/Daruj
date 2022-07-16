package net.jsoft.daruj.introduction.presentation.viewmodel

sealed class IntroductionTask {
    object SwitchPage : IntroductionTask()
    object Finish : IntroductionTask()
}