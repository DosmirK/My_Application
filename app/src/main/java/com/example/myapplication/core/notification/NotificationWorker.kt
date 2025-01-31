package com.example.myapplication.core.notification

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.core.utils.helpers.NotificationHelper
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            sendNotification(applicationContext)
            Log.d("ololo", "Уведомление успешно отправлено!")
            Result.success()
        } catch (e: Exception) {
            Log.e("ololo", "Ошибка при отправке уведомления", e)
            Result.failure()
        }
    }

    private fun sendNotification(context: Context) {
        val notificationHelper = NotificationHelper(context)
        Log.d("ololo", "Уведомление создается!")
        notificationHelper.createNotification("Напоминание", "Не забудь про ежедневные привычки!")
    }
}

fun scheduleNotificationWork(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(2, TimeUnit.HOURS)
        .build()

    val workManager = WorkManager.getInstance(context)
    workManager.enqueueUniquePeriodicWork(
        "daily_notification_work",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}