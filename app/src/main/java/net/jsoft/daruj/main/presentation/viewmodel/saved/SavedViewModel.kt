package net.jsoft.daruj.main.presentation.viewmodel.saved

import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.main.domain.usecase.DeletePostUseCase
import net.jsoft.daruj.main.domain.usecase.GetSavedPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    getSavedPosts: GetSavedPostsUseCase,
    setPostSaved: SetPostSavedUseCase,
    deletePost: DeletePostUseCase,
    getLocalUser: GetLocalUserUseCase
) : PostsViewModel(
    getPosts = getSavedPosts::invoke,
    setPostSaved = setPostSaved::invoke,
    deletePost = deletePost::invoke,
    getLocalUser = getLocalUser
)