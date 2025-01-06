package com.example.myapplication.domain.usecase.habitusecase

import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.repositories.habitrepository.WriteHabitRepository
import javax.inject.Inject

class WriteHabitUseCase @Inject constructor(
    private val repository: WriteHabitRepository
) {

    suspend fun addHabit(habit: HabitModel) = repository.addHabit(habit)
    suspend fun habitUpdate(habit: HabitModel) = repository.updateHabit(habit)
    suspend fun deleteHabit(habit: HabitModel) = repository.deleteHabit(habit)

}