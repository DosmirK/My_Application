package com.example.myapplication.data.repositories.dayrepository

import android.util.Log
import com.example.myapplication.core.utils.convertors.toDayModel
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.data.db.model.DayData
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
) : AnalyticsRepository {

    override fun getProgressUntilToday(): Flow<List<DayModel>> {
        val endDate = LocalDate.now()

        return dao.getProgressUntilToday(endDate.toString())
            .map { dayDataList ->
                val firstDate = dayDataList.minByOrNull { LocalDate.parse(it.date) }?.date
                    ?: endDate.toString()
                Log.d("fefer", "первая запись в БД: $firstDate")

                val startDate = LocalDate.parse(firstDate)
                val today = LocalDate.now()

                val allDates = generateSequence(startDate) { it.plusDays(1) }
                    .takeWhile { it <= today }
                    .map { it.toString() }
                    .toList()

                val existingDates = dayDataList.map { it.date }.toSet()
                val missingDates = allDates.filter { it !in existingDates }

                Log.d("fefer", "записи в БД: $existingDates  \nотсутствующие дни: $missingDates")

                val missingEntries = missingDates.map { date ->
                    DayData(date = date, isCompleted = 0)
                }

                (dayDataList + missingEntries)
                    .sortedBy { LocalDate.parse(it.date) }
                    .map { it.toDayModel() }
            }
    }
}
