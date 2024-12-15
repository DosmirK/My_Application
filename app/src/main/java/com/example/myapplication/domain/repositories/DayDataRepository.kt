package com.example.myapplication.domain.repositories

import com.example.myapplication.domain.model.DayModel
import java.time.LocalDate

interface DayDataRepository {

    suspend fun insertOrUpdateHabitDay(habitDay: DayModel)

    suspend fun getHabitDayByDate(date: String): DayModel?

    suspend fun getAllHabitDays(): List<DayModel>

    suspend fun getProgressForMonth(date: LocalDate): Map<String, Boolean>

}