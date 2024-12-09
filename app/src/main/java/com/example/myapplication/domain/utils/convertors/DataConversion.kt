package com.example.myapplication.domain.utils.convertors

import com.example.myapplication.data.db.model.DayData
import com.example.myapplication.data.db.model.Habit
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.model.HabitModel

fun HabitModel.toHabit(): Habit {
    return Habit(
        id = id,
        name = name,
        isCompleted = isCompleted
    )
}

fun DayModel.toDayData(): DayData {
    return DayData(
        date = date,
        isCompleted = isCompleted
    )
}