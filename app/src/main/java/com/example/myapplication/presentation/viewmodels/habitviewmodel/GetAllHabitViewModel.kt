package com.example.myapplication.presentation.viewmodels.habitviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.state.DataState
import com.example.myapplication.core.state.UiState
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.usecase.habitusecase.GetAllHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAllHabitViewModel @Inject constructor(
    private val useCase: GetAllHabitUseCase
): ViewModel() {

    private var _habits = MutableStateFlow<UiState<List<HabitModel>>>(UiState.Loading())
    var habits: StateFlow<UiState<List<HabitModel>>> = _habits

    init {
        getAllHabits()
    }

    private fun getAllHabits() {
        viewModelScope.launch {
            useCase.getAllHabits().collect { state ->

                when (state) {
                    is DataState.Loading -> {
                        _habits.value = UiState.Loading()
                    }
                    is DataState.Success -> {
                        if (state.data != null) {
                            _habits.value = UiState.Success(state.data)
                        } else {
                            _habits.value = UiState.Empty()
                        }
                    }
                    is DataState.Error -> {
                        Log.e("ololo", "Error: ${state.message}")
                        _habits.value = UiState.Error(state.message)
                    }
                }
            }
        }
    }

    fun refreshHabits() {
        getAllHabits()
    }

}