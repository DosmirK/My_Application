package com.example.myapplication.domain.repositories.habitrepository

import com.example.myapplication.domain.model.HabitModel

interface WriteHabitRepository {

    suspend fun addHabit(habit: HabitModel)
    suspend fun updateHabit(habit: HabitModel)
    suspend fun deleteHabit(habit: HabitModel)

}