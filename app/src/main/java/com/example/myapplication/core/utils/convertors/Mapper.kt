package com.example.myapplication.core.utils.convertors

import com.example.myapplication.data.db.model.DayData
import com.example.myapplication.data.db.model.Habit
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.model.HabitModel

fun List<Habit>.mapToHabitModel() = this.map {
    HabitModel(
        id = it.id,
        name = it.name,
        isCompleted = it.isCompleted
    )
}

fun List<DayData>.mapToDayModel() = this.map {
    DayModel(
        date = it.date,
        isCompleted = it.isCompleted
    )
}