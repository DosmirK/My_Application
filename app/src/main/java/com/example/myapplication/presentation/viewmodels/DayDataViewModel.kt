package com.example.myapplication.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.usecase.DayDataUseCase
import com.example.myapplication.domain.utils.DataState
import com.example.myapplication.domain.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//может быть не нужным
class DayDataViewModel @Inject constructor(
    private val dayDataUseCase: DayDataUseCase
): ViewModel(){

    private var _dayData = MutableStateFlow<UiState<List<DayModel>>>(UiState.Loading())
    var dayData: StateFlow<UiState<List<DayModel>>> = _dayData

    fun getDataForMonth(month: String){
        viewModelScope.launch{
            dayDataUseCase.getDataForMonth(month).collect {
                Log.e("ololo", "dataViewModel: ${it.data}")

                when (it){
                    is DataState.Loading -> _dayData.value = UiState.Loading()
                    is DataState.Success -> {
                        if (it.data != null) {
                            _dayData.value = UiState.Success(it.data)
                        }else{
                            _dayData.value = UiState.Empty()
                        }
                    }
                    is DataState.Error -> _dayData.value = UiState.Error(it.message)
                    else -> {}
                }
                Log.e("ololo", "data_habits: ${_dayData.value.data.toString()}")
            }
        }
    }
    suspend fun addDayData(dayData: DayModel) = dayDataUseCase.addDayData(dayData)
}