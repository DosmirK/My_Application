package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repositories.DayDataRepositoryImpl
import com.example.myapplication.domain.model.DayModel
import javax.inject.Inject

class GetAllHabitDaysUseCase @Inject constructor(
    private val repository: DayDataRepositoryImpl
) {

    suspend fun execute(): List<DayModel> {
        return repository.getAllHabitDays()
    }

}