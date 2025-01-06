package com.example.myapplication.domain.usecase.habitusecase

import com.example.myapplication.domain.repositories.habitrepository.HabitStatsRepository
import javax.inject.Inject

class HabitStatsUseCase @Inject constructor(
    private val repository: HabitStatsRepository
) {

    suspend fun percentageHabitsCompleted(): Int {
        val totalHabitsCount = repository.getTotalHabitsCount()
        val completedHabitsCount = repository.getCompletedHabitsCount()

        return if (totalHabitsCount > 0) {
            (completedHabitsCount * 100) / totalHabitsCount
        } else {
            0
        }
    }

}