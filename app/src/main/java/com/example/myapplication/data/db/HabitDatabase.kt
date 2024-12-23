package com.example.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.data.db.dao.HabitDataDao
import com.example.myapplication.data.db.model.DayData
import com.example.myapplication.data.db.model.Habit

@Database(
    entities = [Habit::class, DayData::class ], version = 1
)
@TypeConverters(Converters::class)
abstract class HabitDatabase: RoomDatabase() {
    abstract fun getHabitDao(): HabitDataDao
    abstract fun getDayDataDao(): DayDataDao
}