package com.example.myapplication.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dayData")
data class DayData(
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val isCompleted: Boolean
)
