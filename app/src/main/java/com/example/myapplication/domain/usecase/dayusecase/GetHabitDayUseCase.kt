package com.example.myapplication.domain.usecase.dayusecase

import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.dayrepository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetHabitDayUseCase @Inject constructor(
    private val repository: ProgressRepository
) {

    operator fun invoke(date: LocalDate): Flow<List<DayModel>> {
        return repository.getProgressForMonth(date)
    }

}