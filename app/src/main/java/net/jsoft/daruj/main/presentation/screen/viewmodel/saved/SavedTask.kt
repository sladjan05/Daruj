package net.jsoft.daruj.main.presentation.screen.viewmodel.saved

import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.main.presentation.screen.viewmodel.posts.PostsTask

sealed interface SavedTask : PostsTask {
    class ShowError(val message: UiText) : SavedTask
    class ShowInfo(val message: UiText) : SavedTask
}