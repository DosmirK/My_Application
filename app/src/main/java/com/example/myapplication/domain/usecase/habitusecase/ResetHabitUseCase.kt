package com.example.myapplication.domain.usecase.habitusecase

import com.example.myapplication.domain.repositories.habitrepository.HabitResetRepository
import javax.inject.Inject

class ResetHabitUseCase @Inject constructor(
    private val repository: HabitResetRepository
) {

    suspend fun resetHabits() {
        repository.resetHabits()
    }

}