package net.jsoft.daruj.donate_blood.data.source.remote

import android.net.Uri
import net.jsoft.daruj.main.domain.model.Receipt

interface ReceiptApi {
    suspend fun getReceipts(postId: String): List<Receipt>
    suspend fun sendReceiptApprovalRequest(postId: String, pictureUri: Uri)

    suspend fun getReceiptCount(postId: String): Int

    suspend fun signReceipt(
        postId: String,
        userId: String,
        isApproved: Boolean
    )
}