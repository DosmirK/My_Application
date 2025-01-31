package com.example.myapplication.core.utils.convertors

import com.example.myapplication.data.db.model.DayData
import com.example.myapplication.data.db.model.Habit

fun List<Habit>.mapToHabitModel() = map { it.toHabitModel() }

fun List<DayData>.mapToDayModel() = map { it.toDayModel() }