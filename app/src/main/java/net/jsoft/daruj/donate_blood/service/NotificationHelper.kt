package net.jsoft.daruj.donate_blood.service

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import net.jsoft.daruj.R
import net.jsoft.daruj.common.util.getValue
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    private val application: Application
) {
    private var areChannelsCreated = false

    fun createChannels() {
        if (areChannelsCreated) return

        createNotificationChannel(Channel.ReceiptApprovalChannel)
        createNotificationChannel(Channel.PendingReceiptChannel)
        createNotificationChannel(Channel.NewCommentsChannel)

        areChannelsCreated = true
    }

    fun createNotification(
        channel: Channel,
        title: String,
        description: String
    ): Notification {
        val style = NotificationCompat.BigTextStyle().bigText(description)

        return NotificationCompat.Builder(application, channel.name)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setColor(application.getColor(R.color.red_26))
            .setContentTitle(title)
            .setContentText(description)
            .setStyle(style)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
    }

    fun postNotification(notification: Notification): Int {
        val id = Random().nextInt()
        NotificationManagerCompat.from(application).notify(id, notification)

        return id
    }

    private fun createNotificationChannel(channel: Channel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(
                channel.name,
                channel.resId.getValue(application),
                importance
            )

            val notificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    enum class Channel(
        @StringRes val resId: Int
    ) {
        ReceiptApprovalChannel(R.string.tx_receipt_approval_channel_name),
        PendingReceiptChannel(R.string.tx_pending_receipt_channel_name),
        NewCommentsChannel(R.string.tx_new_comments_channel_name)
    }
}