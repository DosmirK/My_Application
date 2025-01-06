package com.example.myapplication.domain.repositories.dayrepository

import com.example.myapplication.domain.model.DayModel

interface DayReadOnlyRepository {

    suspend fun getAllHabitDays(): List<DayModel>

}