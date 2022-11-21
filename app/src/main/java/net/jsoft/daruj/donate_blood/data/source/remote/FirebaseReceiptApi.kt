package net.jsoft.daruj.donate_blood.data.source.remote

import android.app.Application
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.common.data.source.remote.FirebaseUserApi
import net.jsoft.daruj.common.domain.model.canDonateBlood
import net.jsoft.daruj.common.domain.model.daysUntilRecovery
import net.jsoft.daruj.common.misc.JsonParser
import net.jsoft.daruj.common.util.compressToByteArray
import net.jsoft.daruj.common.util.getBitmap
import net.jsoft.daruj.donate_blood.exception.BloodNotRecoveredException
import net.jsoft.daruj.main.domain.model.Receipt
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseReceiptApi @Inject constructor(
    private val application: Application,

    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val functions: FirebaseFunctions,

    private val userApi: FirebaseUserApi,

    private val jsonParser: JsonParser
) : ReceiptApi {

    private val receiptsStorage: StorageReference
        get() = storage.reference.child(Receipts)

    override suspend fun getReceipts(postId: String): List<Receipt> = coroutineScope {
        val receiptReferences = receiptsStorage
            .child(postId)
            .listAll()
            .await()
            .items
            .asReversed()

        receiptReferences.map { receiptReference ->
            val deferredDownloadUrl = receiptReference.downloadUrl.asDeferred()
            val deferredMetadata = receiptReference.metadata.asDeferred()
            val deferredUser = async {
                val userId = receiptReference.name.removeSuffix(".png")
                userApi.getUser(userId)
            }

            val creationMillis = deferredMetadata.await().creationTimeMillis
            val instant = Instant.ofEpochMilli(creationMillis)

            Receipt(
                pictureUri = deferredDownloadUrl.await(),
                user = deferredUser.await(),
                timestamp = instant.atZone(ZoneId.of("UTC"))
            )
        }
    }

    override suspend fun sendReceiptApprovalRequest(
        postId: String,
        pictureUri: Uri
    ) {
        val localUser = userApi.getLocalUser()
        if (!localUser.canDonateBlood) throw BloodNotRecoveredException(localUser.daysUntilRecovery)

        val bitmap = application.getBitmap(
            uri = pictureUri,
            width = 800,
            height = 400
        )

        val compressed = bitmap?.compressToByteArray()

        val metadata = StorageMetadata.Builder()
            .setContentType("image/png")
            .build()

        receiptsStorage
            .child(postId)
            .child("${auth.currentUser!!.uid}.png")
            .putBytes(compressed!!, metadata)
            .await()
    }

    override suspend fun getReceiptCount(postId: String): Int {
        return receiptsStorage
            .child(postId)
            .listAll()
            .await()
            .items
            .size
    }

    override suspend fun signReceipt(
        postId: String,
        userId: String,
        isApproved: Boolean
    ) {
        val data = object {
            val postId = postId
            val userId = userId
            val isApproved = isApproved
        }

        functions
            .getHttpsCallable("signReceipt")
            .call(JSONObject(jsonParser.toJson(data)))
            .await()
    }

    companion object {
        private const val Receipts = "receipts"
    }
}