package net.jsoft.daruj.start.presentation.viewmodel

sealed interface StartingTask {
    object ShowWelcomeScreen : StartingTask
    object ShowAuthScreen : StartingTask
    object ShowCreateAccountScreen : StartingTask
    object ShowMainScreen : StartingTask
}