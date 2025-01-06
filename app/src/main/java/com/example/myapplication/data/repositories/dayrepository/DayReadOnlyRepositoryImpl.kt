package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.core.utils.convertors.mapToDayModel
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayReadOnlyRepository
import javax.inject.Inject

class DayReadOnlyRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
): DayReadOnlyRepository {

    override suspend fun getAllHabitDays(): List<DayModel> {
        return dao.getAllHabitDays().mapToDayModel()
    }

}