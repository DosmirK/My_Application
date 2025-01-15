package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.core.utils.convertors.toDayData
import com.example.myapplication.core.utils.convertors.toDayModel
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayWriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DayWriteRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
) : DayWriteRepository {

    override suspend fun insertOrUpdateHabitDay(habitDay: DayModel) {
        dao.insertOrUpdate(habitDay.toDayData())
    }

    override fun getDayByDate(date: String): Flow<DayModel?> {
        return dao.getDayByDate(date).map { it?.toDayModel() }
    }
}