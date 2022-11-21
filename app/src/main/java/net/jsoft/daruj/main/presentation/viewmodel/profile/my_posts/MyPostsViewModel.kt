package net.jsoft.daruj.main.presentation.viewmodel.profile.my_posts

import dagger.hilt.android.lifecycle.HiltViewModel
import net.jsoft.daruj.common.domain.usecase.GetLocalUserUseCase
import net.jsoft.daruj.main.domain.usecase.DeletePostUseCase
import net.jsoft.daruj.main.domain.usecase.GetMyPostsUseCase
import net.jsoft.daruj.main.domain.usecase.SetPostSavedUseCase
import net.jsoft.daruj.main.presentation.viewmodel.posts.PostsViewModel
import javax.inject.Inject

@HiltViewModel
class MyPostsViewModel @Inject constructor(
    getMyPosts: GetMyPostsUseCase,
    setPostSaved: SetPostSavedUseCase,
    deletePost: DeletePostUseCase,
    getLocalUser: GetLocalUserUseCase
) : PostsViewModel(
    getPosts = getMyPosts::invoke,
    setPostSaved = setPostSaved::invoke,
    deletePost = deletePost::invoke,
    getLocalUser = getLocalUser
)