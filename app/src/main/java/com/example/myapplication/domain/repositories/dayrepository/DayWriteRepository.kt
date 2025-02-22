package com.example.myapplication.domain.repositories.dayrepository

import com.example.myapplication.domain.model.DayModel
import kotlinx.coroutines.flow.Flow

interface DayWriteRepository {

    suspend fun insertOrUpdateHabitDay(habitDay: DayModel)

    fun getDayByDate(date: String): Flow<DayModel?>
}