package net.jsoft.daruj.donate_blood.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import net.jsoft.daruj.R
import net.jsoft.daruj.common.domain.usecase.GetUserUseCase
import net.jsoft.daruj.common.misc.DispatcherProvider
import net.jsoft.daruj.main.domain.model.fullName
import net.jsoft.daruj.main.domain.usecase.GetCommentUseCase
import net.jsoft.daruj.main.domain.usecase.GetPostUseCase
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessageHandlingService : FirebaseMessagingService() {

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var messaging: FirebaseMessaging

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Inject
    lateinit var getPost: GetPostUseCase

    @Inject
    lateinit var getUser: GetUserUseCase

    @Inject
    lateinit var getComment: GetCommentUseCase

    @Inject
    lateinit var notificationHelper: NotificationHelper

    private val scope by lazy { CoroutineScope(dispatcherProvider.io) + SupervisorJob() }

    private val localUserDataReference: DocumentReference
        get() = firestore.collection("userData").document(auth.currentUser!!.uid)

    override fun onMessageReceived(message: RemoteMessage) {
        notificationHelper.createChannels()
        val data = message.data

        scope.launch {
            val notification = when (val serverMessage = ServerMessage.fromData(data)) {
                is ServerMessage.PendingReceipt -> {
                    val postId = serverMessage.postId
                    val userId = serverMessage.userId

                    if (userId == auth.currentUser!!.uid) return@launch

                    val deferredPost = async { getPost(postId) }
                    val deferredUser = async { getUser(userId) }

                    val post = deferredPost.await()
                    val user = deferredUser.await()

                    notificationHelper.createNotification(
                        channel = NotificationHelper.Channel.PendingReceiptChannel,
                        title = getString(R.string.tx_donation_for, post.fullName),
                        description = getString(R.string.tx_male_sent_receipt, user.displayName)
                    )
                }

                is ServerMessage.ReceiptApproval -> {
                    val post = getPost(serverMessage.postId)
                    if (post.immutable.user.id == auth.currentUser!!.uid) return@launch

                    notificationHelper.createNotification(
                        channel = NotificationHelper.Channel.ReceiptApprovalChannel,
                        title = post.fullName,
                        description = getString(
                            if (serverMessage.isApproved) {
                                R.string.tx_receipt_approved
                            } else {
                                R.string.tx_receipt_denied
                            }
                        )
                    )
                }

                is ServerMessage.NewComment -> {
                    val comment = getComment(serverMessage.commentId)
                    if (comment.immutable.user.id == auth.currentUser!!.uid) return@launch

                    val post = getPost(serverMessage.postId)

                    notificationHelper.createNotification(
                        channel = NotificationHelper.Channel.NewCommentsChannel,
                        title = post.fullName,
                        description = getString(
                            R.string.tx_comment_format,
                            comment.immutable.user.displayName,
                            comment.mutable.content
                        )
                    )
                }
            }

            notificationHelper.postNotification(notification)
        }
    }

    override fun onNewToken(token: String) {
        scope.launch { if (auth.currentUser != null) sendTokenRegistration(token) }
    }

    private suspend fun sendTokenRegistration(token: String) {
        try {
            localUserDataReference.update("tokens", FieldValue.arrayUnion(token)).await()
            Log.i("TokenRegistration", "Token has been successfully registered.")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}