package com.example.myapplication.data.repositories

import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.DayDataRepository
import com.example.myapplication.domain.utils.convertors.mapToDayModel
import com.example.myapplication.domain.utils.convertors.toDayData
import com.example.myapplication.domain.utils.convertors.toDayModel
import java.time.LocalDate
import javax.inject.Inject

class DayDataRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
): DayDataRepository {

    override suspend fun insertOrUpdateHabitDay(habitDay: DayModel) {
        dao.insertOrUpdate(habitDay.toDayData())
    }

    override suspend fun getHabitDayByDate(date: String): DayModel? {
        return dao.getHabitDayByDate(date)?.toDayModel()
    }

    override suspend fun getAllHabitDays(): List<DayModel> {
        return dao.getAllHabitDays().mapToDayModel()
    }

    override suspend fun getProgressForMonth(date: LocalDate): Map<String, Boolean> {
        val startOfMonth = date.withDayOfMonth(1)
        val endOfMonth = date.withDayOfMonth(date.month.length(date.isLeapYear))
        val progressList = dao.getProgressBetweenDates(startOfMonth, endOfMonth)

        return progressList.associate { habit ->
            habit.date.toString() to habit.isCompleted
        }
    }

}