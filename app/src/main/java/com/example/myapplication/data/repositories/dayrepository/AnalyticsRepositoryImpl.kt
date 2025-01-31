package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.core.utils.convertors.mapToDayModel
import com.example.myapplication.data.db.dao.DayDataDao
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
        val endDate = LocalDate.now().toString()
        return dao.getProgressUntilToday(endDate).map { it.mapToDayModel() }
    }

}