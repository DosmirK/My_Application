package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.DayModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _habitProgress = MutableLiveData<List<DayModel>>()
    val habitProgress: LiveData<List<DayModel>> get() = _habitProgress

    fun updateProgress(days: List<DayModel>) {
        _habitProgress.value = days
    }
}