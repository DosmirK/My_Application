package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repositories.DayDataRepositoryImpl
import com.example.myapplication.domain.model.DayModel
import java.time.LocalDate
import javax.inject.Inject

class GetHabitDayUseCase @Inject constructor(
    private val repository: DayDataRepositoryImpl
) {

    suspend operator fun invoke(date: LocalDate): Map<String, Boolean> {
        return repository.getProgressForMonth(date)
    }

}