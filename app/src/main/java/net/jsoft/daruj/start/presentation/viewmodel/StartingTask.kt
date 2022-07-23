package net.jsoft.daruj.start.presentation.viewmodel

sealed class StartingTask {
    object ShowWelcomeScreen : StartingTask()
    object ShowAuthScreen : StartingTask()
}