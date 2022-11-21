package net.jsoft.daruj.donate_blood.domain.usecase

import android.net.Uri
import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.donate_blood.domain.repository.ReceiptRepository
import javax.inject.Inject

@ViewModelScoped
class SendReceiptApprovalRequestUseCase @Inject constructor(
    private val receiptRepository: ReceiptRepository
) {
    suspend operator fun invoke(postId: String, pictureUri: Uri) {
        receiptRepository.sendReceiptApprovalRequest(postId, pictureUri)
    }
}