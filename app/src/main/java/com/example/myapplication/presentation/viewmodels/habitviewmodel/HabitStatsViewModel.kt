package com.example.myapplication.presentation.viewmodels.habitviewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.usecase.habitusecase.HabitStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HabitStatsViewModel @Inject constructor(
    private val useCase: HabitStatsUseCase
) : ViewModel() {

   fun percentageHabitsCompleted(): Flow<Int> = useCase.percentageHabitsCompleted()

}