package com.example.myapplication.domain.repositories.dayrepository

import java.time.LocalDate

interface ProgressRepository {

    suspend fun getProgressForMonth(date: LocalDate): Map<String, Boolean>

}