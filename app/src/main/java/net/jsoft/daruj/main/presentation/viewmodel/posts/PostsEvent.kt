package net.jsoft.daruj.main.presentation.viewmodel.posts

import net.jsoft.daruj.main.domain.model.Post

interface PostsEvent {
    object Refresh : PostsEvent

    data class SaveClick(val post: Post) : PostsEvent
    data class DeleteClick(val post: Post) : PostsEvent

    object ReachedEnd : PostsEvent
}