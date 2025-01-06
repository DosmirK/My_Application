package com.example.myapplication.data.repositories.habitrepository

import com.example.myapplication.data.db.dao.HabitDataDao
import com.example.myapplication.domain.repositories.habitrepository.HabitStatsRepository
import javax.inject.Inject

class HabitStatsRepositoryImpl @Inject constructor(
    private val habitDao: HabitDataDao
): HabitStatsRepository {

    override suspend fun getTotalHabitsCount(): Int = habitDao.getTotalHabitsCount()
    override suspend fun getCompletedHabitsCount(): Int = habitDao.getCompletedHabitsCount()

}