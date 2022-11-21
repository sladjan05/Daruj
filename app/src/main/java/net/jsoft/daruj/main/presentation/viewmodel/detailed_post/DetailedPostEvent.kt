package net.jsoft.daruj.main.presentation.viewmodel.detailed_post

sealed interface DetailedPostEvent {
    object SaveClick : DetailedPostEvent
    object DeleteClick : DetailedPostEvent
}