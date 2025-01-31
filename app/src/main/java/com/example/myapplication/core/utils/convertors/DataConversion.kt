package com.example.myapplication.core.utils.convertors

import com.example.myapplication.data.db.model.DayData
import com.example.myapplication.data.db.model.Habit
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.model.HabitModel

fun HabitModel.toHabit() = Habit(id, name, isCompleted)

fun DayModel.toDayData() = DayData(date, isCompleted)

fun DayData.toDayModel() = DayModel(date, isCompleted)

fun Habit.toHabitModel() = HabitModel(id, name, isCompleted)