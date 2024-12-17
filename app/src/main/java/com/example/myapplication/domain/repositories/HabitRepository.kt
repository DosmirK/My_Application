package com.example.myapplication.domain.repositories

import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    suspend fun resetHabits()

    suspend fun getAllHabits() : Flow<DataState<List<HabitModel>>>
    suspend fun addHabit(habit: HabitModel)
    suspend fun updateHabit(habit: HabitModel)
    suspend fun deleteHabit(habit: HabitModel)
    suspend fun getTotalHabitsCount(): Int
    suspend fun getCompletedHabitsCount(): Int
}