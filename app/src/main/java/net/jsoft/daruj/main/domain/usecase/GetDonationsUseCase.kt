package net.jsoft.daruj.main.domain.usecase

import net.jsoft.daruj.main.domain.model.Donation
import net.jsoft.daruj.main.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDonationsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(reset: Boolean = false): List<Donation> {
        return postRepository.getDonations(reset)
    }
}