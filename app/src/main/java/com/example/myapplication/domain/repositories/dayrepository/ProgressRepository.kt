package com.example.myapplication.domain.repositories.dayrepository

import com.example.myapplication.domain.model.DayModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ProgressRepository {

    fun getProgressForMonth(date: LocalDate): Flow<List<DayModel>>

}