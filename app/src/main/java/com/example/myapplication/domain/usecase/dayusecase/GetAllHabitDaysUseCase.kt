package com.example.myapplication.domain.usecase.dayusecase

import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayReadOnlyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHabitDaysUseCase @Inject constructor(
    private val repository: DayReadOnlyRepository
) {

    fun execute(): Flow<List<DayModel>> {
        return repository.getAllHabitDays()
    }
}