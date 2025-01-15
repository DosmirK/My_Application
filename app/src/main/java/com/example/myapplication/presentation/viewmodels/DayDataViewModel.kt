package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.usecase.dayusecase.GetAllHabitDaysUseCase
import com.example.myapplication.domain.usecase.dayusecase.GetHabitDayUseCase
import com.example.myapplication.domain.usecase.dayusecase.SaveHabitDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayDataViewModel @Inject constructor(
    private val getHabitDayUseCase: GetHabitDayUseCase,
    private val saveHabitDayUseCase: SaveHabitDayUseCase,
    private val getAllHabitDaysUseCase: GetAllHabitDaysUseCase
) : ViewModel() {

    private val _habitDays = MutableStateFlow<List<DayModel>>(emptyList())
    val habitDays: StateFlow<List<DayModel>> get() = _habitDays

    private val _habitsProgress = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val habitsProgress: StateFlow<Map<String, Boolean>> get() = _habitsProgress

    fun loadHabitDays() {
        viewModelScope.launch {
            getAllHabitDaysUseCase.execute()
                .collect { days ->
                    _habitDays.value = days
                }
        }
    }

    fun saveHabitDay(habitDay: DayModel) {
        viewModelScope.launch {
            saveHabitDayUseCase.execute(habitDay)
                .collect {
                    loadHabitDays()
                }
        }
    }

    fun fetchHabitsProgress(date: LocalDate) {
        viewModelScope.launch {
            getHabitDayUseCase(date)
                .collect { progressData ->
                    _habitsProgress.value = progressData
                }
        }
    }
}