package com.example.myapplication.domain.repositories

import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DayDataRepository {

    suspend fun getProgressDataForMonth(month: String) : Flow<DataState<List<DayModel>>>
    suspend fun addHabit(dayData: DayModel)
}