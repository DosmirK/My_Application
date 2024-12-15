package com.example.myapplication.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.domain.usecase.GetAllHabitDaysUseCase
import com.example.myapplication.domain.usecase.HabitUseCase
import com.example.myapplication.domain.usecase.SaveHabitDayUseCase
import com.example.myapplication.domain.utils.DataState
import com.example.myapplication.domain.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitUseCase: HabitUseCase,
    private val saveHabitDayUseCase: SaveHabitDayUseCase,
    private val getAllHabitDaysUseCase: GetAllHabitDaysUseCase
) : ViewModel() {

    private var _habits = MutableStateFlow<UiState<List<HabitModel>>>(UiState.Loading())
    var habits: StateFlow<UiState<List<HabitModel>>> = _habits

    private val _habitDays = MutableLiveData<List<DayModel>>()
    val habitDays: LiveData<List<DayModel>> get() = _habitDays

    fun getAllHabits(){
        viewModelScope.launch{
            habitUseCase.getAllHabits().collect {
                Log.e("ololo", "dataViewModel: ${it.data}")

                when (it){
                    is DataState.Loading -> _habits.value = UiState.Loading()
                    is DataState.Success -> {
                        if (it.data != null) {
                            _habits.value = UiState.Success(it.data)
                        }else{
                            _habits.value = UiState.Empty()
                        }
                    }
                    is DataState.Error -> _habits.value = UiState.Error(it.message)
                }
                Log.e("ololo", "data_habits: ${_habits.value.data.toString()}")
            }
        }
    }

    suspend fun addHabit(habit: HabitModel) = habitUseCase.addHabit(habit)
    suspend fun deleteHabit(habit: HabitModel) = habitUseCase.deleteHabit(habit)
    fun updateHabit(habit: HabitModel) {
        viewModelScope.launch {
            habitUseCase.habitUpdate(habit)
        }
    }

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

    suspend fun percentageHabitsCompleted(): Int = habitUseCase.percentageHabitsCompleted()
}