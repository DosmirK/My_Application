package com.example.myapplication.domain.repositories.dayrepository

import com.example.myapplication.domain.model.DayModel

interface DayWriteRepository {

    suspend fun insertOrUpdateHabitDay(habitDay: DayModel)
    suspend fun getDayByDate(date: String): DayModel?

}