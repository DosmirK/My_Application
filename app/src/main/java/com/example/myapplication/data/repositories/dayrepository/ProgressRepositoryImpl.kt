package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.repositories.dayrepository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ProgressRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
) : ProgressRepository {

    override fun getProgressForMonth(date: LocalDate): Flow<Map<String, Boolean>> {
        val startOfMonth = date.withDayOfMonth(1)
        val endOfMonth = date.withDayOfMonth(date.month.length(date.isLeapYear))

        return dao.getProgressBetweenDates(startOfMonth, endOfMonth)
            .map { progressList ->
                progressList.associate { habit ->
                    habit.date to habit.isCompleted
                }
            }
    }
}