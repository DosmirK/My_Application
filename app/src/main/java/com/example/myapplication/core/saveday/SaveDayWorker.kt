package com.example.myapplication.core.saveday

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.myapplication.data.db.HabitDatabase
import com.example.myapplication.data.repositories.dayrepository.DayWriteRepositoryImpl
import com.example.myapplication.domain.model.DayModel
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import java.util.concurrent.TimeUnit

class SaveDayWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val dayRepository: DayWriteRepositoryImpl

    init {
        val database = HabitDatabase.getInstance(context)
        dayRepository = DayWriteRepositoryImpl(database.getDayDataDao())
    }

    override suspend fun doWork(): Result {
        Log.d("ololo", "doWork")
        return try {
            val currentDate = "2025-01-10"
            Log.d("ololo", "Получена дата: $currentDate")

            val existingDay = dayRepository.getDayByDate(currentDate).firstOrNull()

            Log.d("ololo", "Получена дата из dao: $existingDay")

            if (existingDay == null) {
                val newDay = DayModel(
                    date = currentDate,
                    isCompleted = 0
                )
                dayRepository.addHabitDay(newDay)
                Log.d("SaveDayWorker", "Добавлена запись: $newDay")
            } else {
                Log.d("SaveDayWorker", "Запись для $currentDate уже существует")
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("SaveDayWorker", "Ошибка выполнения Worker: ${e.message}", e)
            Result.failure()
        }
    }
}

fun scheduleEndOfDayWork(context: Context) {

    val now = Calendar.getInstance()
    val endOfDay = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 18)
        set(Calendar.MINUTE, 13)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val delay = endOfDay.timeInMillis - now.timeInMillis
    if (delay > 0) {
        val workRequest = OneTimeWorkRequestBuilder<SaveDayWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork(
            "end_of_day_work",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )

        Log.d("ololo", "Задача запланирована через ${delay / 1000 / 60} минут")
    } else {
        Log.d("ololo", "Слишком поздно для планирования задачи на сегодня")
    }
}