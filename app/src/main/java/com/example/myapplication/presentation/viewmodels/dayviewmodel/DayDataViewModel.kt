package com.example.myapplication.presentation.viewmodels.dayviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.usecase.dayusecase.GetHabitDayUseCase
import com.example.myapplication.domain.usecase.dayusecase.SaveHabitDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayDataViewModel @Inject constructor(
    private val getHabitDayUseCase: GetHabitDayUseCase,
    private val saveHabitDayUseCase: SaveHabitDayUseCase,
) : ViewModel() {

    private val _habitsProgress = MutableStateFlow<List<DayModel>>(emptyList())
    val habitsProgress: StateFlow<List<DayModel>> get() = _habitsProgress

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    fun updateSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    init {
        viewModelScope.launch {
            _selectedDate.collectLatest { date ->
                fetchHabitsProgress(date)
                Log.d("simba", "вызов collectLatest в Init \n дата: $date")
            }
        }
    }

    fun saveHabitDay(habitDay: DayModel) {
        viewModelScope.launch {
            saveHabitDayUseCase.execute(habitDay)
        }
    }

    private fun fetchHabitsProgress(date: LocalDate) {
        viewModelScope.launch {
            Log.d("simba", "отправленная дата: $date")
            getHabitDayUseCase(date)
                .catch { emit(emptyList()) }
                .collect { data ->
                    Log.d("simba", "вызов collectLatest во ViewModel: $data \n дата: $date")
                    val habitDays = data.map { (date, isCompleted) -> DayModel(date, isCompleted) }
                    if (_habitsProgress.value != habitDays) {
                        _habitsProgress.value = habitDays
                    }
                }
        }
    }

}