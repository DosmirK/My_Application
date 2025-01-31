package com.example.myapplication.domain.repositories.dayrepository

import com.example.myapplication.domain.model.DayModel
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {

    fun getProgressUntilToday(): Flow<List<DayModel>>

}