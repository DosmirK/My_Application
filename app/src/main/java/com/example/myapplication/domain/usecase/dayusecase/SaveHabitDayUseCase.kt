package com.example.myapplication.domain.usecase.dayusecase

import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayWriteRepository
import javax.inject.Inject

class SaveHabitDayUseCase @Inject constructor(
    private val repository: DayWriteRepository
) {

    suspend fun execute(habitDay: DayModel) {
        repository.insertOrUpdateHabitDay(habitDay)
    }

}