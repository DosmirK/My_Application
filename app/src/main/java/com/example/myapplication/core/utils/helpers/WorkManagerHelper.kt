package com.example.myapplication.core.utils.helpers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.core.SaveDayWorker
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    fun scheduleDailyWork(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<SaveDayWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "save_day_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun calculateInitialDelay(): Long {
        val now = LocalDateTime.now()
        val targetTime = now.toLocalDate().atTime(23, 59)
        return if (now.isAfter(targetTime)) {
            Duration.between(now, targetTime.plusDays(1)).toMillis()
        } else {
            Duration.between(now, targetTime).toMillis()
        }
    }

}