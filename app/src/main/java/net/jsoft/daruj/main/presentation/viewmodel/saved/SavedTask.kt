package net.jsoft.daruj.main.presentation.viewmodel.saved

import net.jsoft.daruj.common.misc.UiText
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsTask

sealed interface SavedTask : PostsTask {
    data class ShowError(val message: UiText) : SavedTask
}