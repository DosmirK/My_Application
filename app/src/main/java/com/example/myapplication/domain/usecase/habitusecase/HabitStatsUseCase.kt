package com.example.myapplication.domain.usecase.habitusecase

import com.example.myapplication.domain.repositories.habitrepository.HabitStatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HabitStatsUseCase @Inject constructor(
    private val repository: HabitStatsRepository
) {

    fun percentageHabitsCompleted(): Flow<Int> = flow {
        emit(0)

        try {
            val totalHabitsCountFlow = repository.getTotalHabitsCount()
            val completedHabitsCountFlow = repository.getCompletedHabitsCount()

            totalHabitsCountFlow.collect { totalHabitsCount ->
                completedHabitsCountFlow.collect { completedHabitsCount ->
                    val percentage = if (totalHabitsCount > 0) {
                        (completedHabitsCount * 100) / totalHabitsCount
                    } else {
                        0
                    }
                    emit(percentage)
                }
            }

        } catch (e: Exception) {
            emit(0)
        }
    }
}