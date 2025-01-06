package com.example.myapplication.domain.repositories.habitrepository

import com.example.myapplication.core.state.DataState
import com.example.myapplication.domain.model.HabitModel
import kotlinx.coroutines.flow.Flow

interface ReadOnlyHabitRepository {

    suspend fun getAllHabits() : Flow<DataState<List<HabitModel>>>

}