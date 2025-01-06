package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.repositories.dayrepository.ProgressRepository
import java.time.LocalDate
import javax.inject.Inject

class ProgressRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
): ProgressRepository {

    override suspend fun getProgressForMonth(date: LocalDate): Map<String, Boolean> {
        val startOfMonth = date.withDayOfMonth(1)
        val endOfMonth = date.withDayOfMonth(date.month.length(date.isLeapYear))
        val progressList = dao.getProgressBetweenDates(startOfMonth, endOfMonth)

        return progressList.associate { habit ->
            habit.date to habit.isCompleted
        }
    }

}