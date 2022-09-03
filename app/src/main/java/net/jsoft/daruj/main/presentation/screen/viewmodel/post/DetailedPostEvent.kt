package net.jsoft.daruj.main.presentation.screen.viewmodel.post

sealed interface DetailedPostEvent {
    object SaveClick : DetailedPostEvent
}