package com.example.myapplication.presentation.viewmodels.habitviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.common.DateManager
import com.example.myapplication.domain.usecase.habitusecase.ResetHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetHabitViewModel @Inject constructor(
    private val useCase: ResetHabitUseCase,
    private val dateManager: DateManager
): ViewModel() {

    fun resetAllHabits() {
        viewModelScope.launch {
            useCase.resetHabits()
        }
    }

    fun isDateChanged(): Boolean {
        return dateManager.isDateChanged()
    }

}