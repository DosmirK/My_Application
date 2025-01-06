package com.example.myapplication.domain.usecase.dayusecase

import com.example.myapplication.domain.repositories.dayrepository.ProgressRepository
import java.time.LocalDate
import javax.inject.Inject

class GetHabitDayUseCase @Inject constructor(
    private val repository: ProgressRepository
) {

    suspend operator fun invoke(date: LocalDate): Map<String, Boolean> {
        return repository.getProgressForMonth(date)
    }

}