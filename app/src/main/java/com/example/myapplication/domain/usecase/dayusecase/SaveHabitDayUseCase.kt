package com.example.myapplication.domain.usecase.dayusecase

import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.DayWriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveHabitDayUseCase @Inject constructor(
    private val repository: DayWriteRepository
) {

    fun execute(habitDay: DayModel): Flow<Unit> {
        return flow {
            repository.insertOrUpdateHabitDay(habitDay)
            emit(Unit)
        }
    }
}