package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.db.dao.HabitDataDao
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.repositories.HabitRepository
import com.example.myapplication.domain.utils.DataState
import com.example.myapplication.domain.utils.convertors.mapToHabitModel
import com.example.myapplication.domain.utils.convertors.toHabit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDataDao
): HabitRepository {

    override suspend fun resetHabits() {
        habitDao.resetAllHabits()
    }

    override suspend fun getAllHabits(): Flow<DataState<List<HabitModel>>> =
        flow {
            emit(DataState.Loading())
            try {
                val data = habitDao.getAllHabits().mapToHabitModel()
                emit(DataState.Success(data))
                Log.e("ololo", "dataRepost: $data")
            } catch (e: Exception) {
                emit(DataState.Error(e.localizedMessage))
                Log.e("ololo", "errorRepost: ${e.localizedMessage}")
            }
        }

    override suspend fun addHabit(habit: HabitModel) = habitDao.addHabit(habit.toHabit())
    override suspend fun updateHabit(habit: HabitModel) = habitDao.update(habit.toHabit())
    override suspend fun deleteHabit(habit: HabitModel) = habitDao.remove(habit.toHabit())
    override suspend fun getTotalHabitsCount(): Int = habitDao.getTotalHabitsCount()
    override suspend fun getCompletedHabitsCount(): Int = habitDao.getCompletedHabitsCount()
}