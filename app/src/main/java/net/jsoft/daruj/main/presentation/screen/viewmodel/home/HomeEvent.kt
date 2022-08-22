package net.jsoft.daruj.main.presentation.screen.viewmodel.home

import net.jsoft.daruj.main.domain.model.Post

sealed class HomeEvent {
    object Refresh : HomeEvent()

    class SaveClick(val post: Post) : HomeEvent()
}
