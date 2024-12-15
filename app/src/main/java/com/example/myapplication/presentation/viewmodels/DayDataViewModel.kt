package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.usecase.GetAllHabitDaysUseCase
import com.example.myapplication.domain.usecase.GetHabitDayUseCase
import com.example.myapplication.domain.usecase.SaveHabitDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayDataViewModel @Inject constructor(
    private val getHabitDayUseCase: GetHabitDayUseCase,
    private val saveHabitDayUseCase: SaveHabitDayUseCase,
    private val getAllHabitDaysUseCase: GetAllHabitDaysUseCase
) : ViewModel() {

    private val _habitDays = MutableLiveData<List<DayModel>>()
    val habitDays: LiveData<List<DayModel>> get() = _habitDays

    private val _habitsProgress = MutableLiveData<Map<String, Boolean>>()
    val habitsProgress: LiveData<Map<String, Boolean>> get() = _habitsProgress

    fun loadHabitDays() {
        viewModelScope.launch {
            val days = getAllHabitDaysUseCase.execute()
            _habitDays.value = days
        }
    }

    fun saveHabitDay(habitDay: DayModel) {
        viewModelScope.launch {
            saveHabitDayUseCase.execute(habitDay)
            loadHabitDays()
        }
    }

    fun fetchHabitsProgress(date: LocalDate) {
        viewModelScope.launch {
            val progressData = getHabitDayUseCase(date)
            _habitsProgress.value = progressData
        }
    }
}