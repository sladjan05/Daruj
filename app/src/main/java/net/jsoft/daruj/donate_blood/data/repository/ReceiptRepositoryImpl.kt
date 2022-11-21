package net.jsoft.daruj.donate_blood.data.repository

import android.net.Uri
import kotlinx.coroutines.withContext
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.donate_blood.data.source.remote.ReceiptApi
import net.jsoft.daruj.donate_blood.domain.repository.ReceiptRepository
import net.jsoft.daruj.main.domain.model.Receipt

class ReceiptRepositoryImpl(
    private val receiptApi: ReceiptApi,
    private val dispatcherProvider: DispatcherProvider
) : ReceiptRepository {

    override suspend fun getReceipts(postId: String): List<Receipt> =
        withContext(dispatcherProvider.io) {
            receiptApi.getReceipts(postId)
        }

    override suspend fun sendReceiptApprovalRequest(
        postId: String,
        pictureUri: Uri
    ) = withContext(dispatcherProvider.io) {
        receiptApi.sendReceiptApprovalRequest(postId, pictureUri)
    }

    override suspend fun getReceiptCount(postId: String): Int =
        withContext(dispatcherProvider.io) {
            receiptApi.getReceiptCount(postId)
        }

    override suspend fun signReceipt(
        postId: String,
        userId: String,
        isApproved: Boolean
    ) = withContext(dispatcherProvider.io) {
        receiptApi.signReceipt(postId, userId, isApproved)
    }
}