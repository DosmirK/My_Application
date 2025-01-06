package com.example.myapplication.domain.usecase.habitusecase

import android.util.Log
import com.example.myapplication.core.state.DataState
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.repositories.habitrepository.ReadOnlyHabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class GetAllHabitUseCase @Inject constructor(
    private val repository: ReadOnlyHabitRepository
) {

    suspend fun getAllHabits(): Flow<DataState<List<HabitModel>>> {

        val habits = repository.getAllHabits()
        val data = habits.toList()
        Log.e("ololo", "dataUes: $data")
        return habits
    }

}