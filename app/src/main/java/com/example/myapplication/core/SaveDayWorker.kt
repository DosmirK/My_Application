package com.example.myapplication.core

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayWriteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject

@HiltWorker
class SaveDayWorker @Inject constructor(
    @ApplicationContext private val context: Context,
    workerParams: WorkerParameters,
    private val dayRepository: DayWriteRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val currentDate = LocalDate.now().toString()

            val existingDay = dayRepository.getDayByDate(currentDate)
            if (existingDay == null) {
                val newDay = DayModel(
                    date = currentDate,
                    isCompleted = false
                )
                dayRepository.insertOrUpdateHabitDay(newDay)
                Log.d("SaveDayWorker", "Добавлена запись: $newDay")
            } else {
                Log.d("SaveDayWorker", "Запись для $currentDate уже существует")
            }
            return Result.success()
        } catch (e: Exception) {
            Log.e("SaveDayWorker", "Ошибка выполнения Worker: ${e.message}", e)
            return Result.failure()
        }
    }

}