package com.example.myapplication.core.utils.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import com.example.myapplication.presentation.activity.MainActivity

class NotificationHelper(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "MyChannelId"
        private const val CHANNEL_NAME = "MyChannel"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
        if (existingChannel == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Этот канал используется для отправки уведомлений."
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(
        title: String,
        message: String,
        intent: Intent = Intent(context, MainActivity::class.java),
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(notificationId, notification)
            Log.d("NotificationHelper", "Уведомление отправлено! ID: $notificationId")
        } catch (e: Exception) {
            Log.e("NotificationHelper", "Ошибка при отправке уведомления: ${e.localizedMessage}")
        }
    }
}