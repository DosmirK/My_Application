package com.example.myapplication.domain.usecase.habitusecase

import com.example.myapplication.core.state.DataState
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.repositories.habitrepository.ReadOnlyHabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHabitUseCase @Inject constructor(
    private val repository: ReadOnlyHabitRepository
) {

    suspend fun getAllHabits(): Flow<DataState<List<HabitModel>>> = repository.getAllHabits()

}