package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import java.time.format.DateTimeFormatter

class ProgressRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
) : ProgressRepository {

    override fun getProgressForMonth(date: LocalDate): Flow<List<DayModel>> {
        val startOfMonth = date.withDayOfMonth(1)
        val endOfMonth = date.withDayOfMonth(date.month.length(date.isLeapYear))

        val allDatesInMonth = generateDatesInRange(startOfMonth, endOfMonth)

        return dao.getProgressBetweenDates(startOfMonth.toString(), endOfMonth.toString())
            .map { progressList ->
                val progressMap = progressList.associateBy { LocalDate.parse(it.date, DateTimeFormatter.ISO_DATE) }

                allDatesInMonth.map { date ->
                    val dayData = progressMap[date]
                    if (dayData != null) {
                        DayModel(dayData.date, dayData.isCompleted)
                    } else {
                        DayModel(date.toString(), 0)
                    }
                }
            }
    }

    private fun generateDatesInRange(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var currentDate = startDate

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }

        return dates
    }
}
