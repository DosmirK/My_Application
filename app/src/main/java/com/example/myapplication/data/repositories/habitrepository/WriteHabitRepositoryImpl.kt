package com.example.myapplication.data.repositories.habitrepository

import com.example.myapplication.core.utils.convertors.toHabit
import com.example.myapplication.data.db.dao.HabitDataDao
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.repositories.habitrepository.WriteHabitRepository
import javax.inject.Inject

class WriteHabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDataDao
): WriteHabitRepository {

    override suspend fun addHabit(habit: HabitModel) = habitDao.addHabit(habit.toHabit())
    override suspend fun updateHabit(habit: HabitModel) = habitDao.update(habit.toHabit())
    override suspend fun deleteHabit(habit: HabitModel) = habitDao.remove(habit.toHabit())

}