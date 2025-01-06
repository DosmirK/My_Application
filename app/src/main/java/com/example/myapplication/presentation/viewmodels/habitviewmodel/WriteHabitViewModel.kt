package com.example.myapplication.presentation.viewmodels.habitviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.usecase.habitusecase.WriteHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteHabitViewModel @Inject constructor(
    private val useCase: WriteHabitUseCase
): ViewModel() {

    suspend fun addHabit(habit: HabitModel) = useCase.addHabit(habit)
    suspend fun deleteHabit(habit: HabitModel) = useCase.deleteHabit(habit)
    fun updateHabit(habit: HabitModel) {
        viewModelScope.launch {
            useCase.habitUpdate(habit)
        }
    }

}