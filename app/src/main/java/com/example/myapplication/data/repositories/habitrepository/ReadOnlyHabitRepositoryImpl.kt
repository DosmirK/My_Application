package com.example.myapplication.data.repositories.habitrepository

import android.util.Log
import com.example.myapplication.core.state.DataState
import com.example.myapplication.core.utils.convertors.mapToHabitModel
import com.example.myapplication.data.db.dao.HabitDataDao
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.repositories.habitrepository.ReadOnlyHabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadOnlyHabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDataDao
): ReadOnlyHabitRepository {

    override suspend fun getAllHabits(): Flow<DataState<List<HabitModel>>> =
        flow {
            emit(DataState.Loading())
            try {
                habitDao.getAllHabits()
                    .map { it.mapToHabitModel() }

                    .collect { data ->
                        emit(DataState.Success(data))
                        Log.e("ololo", "dataRepost: $data")
                    }
            } catch (e: Exception) {
                emit(DataState.Error(e.localizedMessage))
                Log.e("ololo", "errorRepost: ${e.localizedMessage}")
            }
        }
}