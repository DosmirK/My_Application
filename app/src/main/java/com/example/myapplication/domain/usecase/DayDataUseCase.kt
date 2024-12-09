package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repositories.DayDataRepositoryImpl
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DayDataUseCase @Inject constructor(
    private val dayDataRepository: DayDataRepositoryImpl
) {
    suspend fun getDataForMonth(month: String): Flow<DataState<List<DayModel>>> = dayDataRepository.getProgressDataForMonth(month)
    suspend fun addDayData(dayData: DayModel) = dayDataRepository.addHabit(dayData)
}