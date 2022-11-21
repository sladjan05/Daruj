package net.jsoft.daruj.main.presentation.viewmodel.posts

import net.jsoft.daruj.common.misc.UiText

interface PostsTask {
    data class ShowError(val message: UiText) : PostsTask
}