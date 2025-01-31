package com.example.myapplication.domain.usecase.dayusecase

import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnalyticsUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {
    operator fun invoke(): Flow<List<DayModel>> {
        return repository.getProgressUntilToday()
    }
}