package com.example.myapplication.domain.repositories.habitrepository

interface HabitStatsRepository {

    suspend fun getTotalHabitsCount(): Int
    suspend fun getCompletedHabitsCount(): Int

}