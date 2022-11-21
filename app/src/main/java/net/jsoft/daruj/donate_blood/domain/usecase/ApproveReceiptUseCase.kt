package net.jsoft.daruj.donate_blood.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.donate_blood.domain.repository.ReceiptRepository
import javax.inject.Inject

@ViewModelScoped
class ApproveReceiptUseCase @Inject constructor(
    private val receiptRepository: ReceiptRepository
) {
    suspend operator fun invoke(
        postId: String,
        userId: String
    ) {
        receiptRepository.signReceipt(
            postId = postId,
            userId = userId,
            isApproved = true
        )
    }
}