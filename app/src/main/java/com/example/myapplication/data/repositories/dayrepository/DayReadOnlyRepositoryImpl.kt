package com.example.myapplication.data.repositories.dayrepository

import com.example.myapplication.core.utils.convertors.mapToDayModel
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayReadOnlyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DayReadOnlyRepositoryImpl @Inject constructor(
    private val dao: DayDataDao
) : DayReadOnlyRepository {

    override fun getAllHabitDays(): Flow<List<DayModel>> {
        return dao.getAllHabitDays()
            .map { it.mapToDayModel() }
    }
}