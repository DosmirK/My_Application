package com.example.myapplication.domain.usecase.dayusecase

import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayReadOnlyRepository
import javax.inject.Inject

class GetAllHabitDaysUseCase @Inject constructor(
    private val repository: DayReadOnlyRepository
) {

    suspend fun execute(): List<DayModel> {
        return repository.getAllHabitDays()
    }

}