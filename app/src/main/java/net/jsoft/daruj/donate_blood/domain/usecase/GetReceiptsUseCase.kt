package net.jsoft.daruj.donate_blood.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import net.jsoft.daruj.donate_blood.domain.repository.ReceiptRepository
import net.jsoft.daruj.main.domain.model.Receipt
import javax.inject.Inject

@ViewModelScoped
class GetReceiptsUseCase @Inject constructor(
    private val receiptRepository: ReceiptRepository
) {
    suspend operator fun invoke(postId: String): List<Receipt> {
        return receiptRepository.getReceipts(postId)
    }
}