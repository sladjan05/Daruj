package net.jsoft.daruj.main.presentation.screen.viewmodel.saved

import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.main.domain.usecase.GetSavedPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.screen.viewmodel.posts.PostsViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    getSavedPosts: GetSavedPostsUseCase,
    setPostSaved: SetPostSavedUseCase
) : PostsViewModel(
    getPosts = getSavedPosts::invoke,
    setPostSaved = setPostSaved::invoke
)