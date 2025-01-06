package com.example.myapplication.data.repositories.habitrepository

import com.example.myapplication.data.db.dao.HabitDataDao
import com.example.myapplication.domain.repositories.habitrepository.HabitResetRepository
import javax.inject.Inject

class HabitResetRepositoryImpl @Inject constructor(
    private val habitDao: HabitDataDao
): HabitResetRepository {

    override suspend fun resetHabits() {
        habitDao.resetAllHabits()
    }

}