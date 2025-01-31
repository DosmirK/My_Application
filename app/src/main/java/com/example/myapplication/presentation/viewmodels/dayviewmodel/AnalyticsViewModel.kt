package com.example.myapplication.presentation.viewmodels.dayviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.usecase.dayusecase.AnalyticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val useCase: AnalyticsUseCase
) : ViewModel() {

    private val _progressData = MutableStateFlow<List<DayModel>>(emptyList())
    val progressData: StateFlow<List<DayModel>> get() = _progressData

    init {
        loadProgressData()
    }

    private fun loadProgressData() {
        viewModelScope.launch {
            useCase().collect {
                _progressData.value = it
            }
        }
    }
}