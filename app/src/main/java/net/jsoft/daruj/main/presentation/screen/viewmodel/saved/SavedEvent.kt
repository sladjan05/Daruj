package net.jsoft.daruj.main.presentation.screen.viewmodel.saved

import net.jsoft.daruj.main.domain.model.Post

sealed class SavedEvent {
    object Refresh : SavedEvent()

    class SaveClick(val post: Post) : SavedEvent()

    object ReachedEnd : SavedEvent()
}
