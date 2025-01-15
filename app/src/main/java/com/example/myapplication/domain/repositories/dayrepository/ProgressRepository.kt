package com.example.myapplication.domain.repositories.dayrepository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ProgressRepository {

    fun getProgressForMonth(date: LocalDate): Flow<Map<String, Boolean>>

}