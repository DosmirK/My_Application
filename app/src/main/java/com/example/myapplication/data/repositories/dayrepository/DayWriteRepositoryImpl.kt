package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.core.utils.convertors.toDayData
import com.example.myapplication.core.utils.convertors.toDayModel
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayWriteRepository
import javax.inject.Inject

class DayWriteRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
): DayWriteRepository {

    override suspend fun insertOrUpdateHabitDay(habitDay: DayModel) {
        dao.insertOrUpdate(habitDay.toDayData())
    }

    override suspend fun getDayByDate(date: String): DayModel? {
        return dao.getDayByDate(date)?.toDayModel()
    }

}