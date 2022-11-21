package net.jsoft.daruj.main.presentation.viewmodel.search

import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsEvent

sealed interface SearchEvent : PostsEvent {
    data class SearchChange(val content: String) : SearchEvent
}