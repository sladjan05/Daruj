package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.common.domain.repository.UserRepository
import net.jsoft.daruj.main.domain.model.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavePostUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(post: Post) {
        val localUser = userRepository.getLocalUser()
        val savedPosts = localUser.mutable.savedPosts.toMutableList()

        if (savedPosts.contains(post.data.id)) {
            savedPosts.remove(post.data.id)
        } else {
            savedPosts.add(post.data.id)
        }

        userRepository.updateLocalUser(localUser.mutable.copy(savedPosts = savedPosts))
    }
}