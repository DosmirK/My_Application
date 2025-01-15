package com.example.myapplication.domain.repositories.habitrepository

import kotlinx.coroutines.flow.Flow

interface HabitStatsRepository {

    suspend fun getTotalHabitsCount(): Flow<Int>
    suspend fun getCompletedHabitsCount(): Flow<Int>

}