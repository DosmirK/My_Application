package com.example.myapplication.domain.usecase

import android.util.Log
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.repositories.HabitRepository
import com.example.myapplication.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class HabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend fun getAllHabits(): Flow<DataState<List<HabitModel>>> {

        val habits = habitRepository.getAllHabits()
        val data = habits.toList()
        Log.e("ololo", "dataUes: ${data}")
        return habits
    }

    suspend fun resetHabits() {
       habitRepository.resetHabits()
    }


    suspend fun addHabit(habit: HabitModel) = habitRepository.addHabit(habit)
    suspend fun habitUpdate(habit: HabitModel) = habitRepository.updateHabit(habit)
    suspend fun deleteHabit(habit: HabitModel) = habitRepository.deleteHabit(habit)

    suspend fun percentageHabitsCompleted(): Int {
        val totalHabitsCount = habitRepository.getTotalHabitsCount()
        val completedHabitsCount = habitRepository.getCompletedHabitsCount()

        return if (totalHabitsCount > 0) {
            (completedHabitsCount * 100) / totalHabitsCount
        } else {
            0
        }
    }
    suspend fun getTotalHabitsCount(): Int = habitRepository.getTotalHabitsCount()
    suspend fun getCompletedHabitsCount(): Int = habitRepository.getCompletedHabitsCount()
}