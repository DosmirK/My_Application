package com.example.myapplication.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dayData")
data class DayData(
    @PrimaryKey val date: String, // Дата в формате "yyyy-MM-dd"
    val isCompleted: Boolean // Цвет, например "#FF0000"
)
