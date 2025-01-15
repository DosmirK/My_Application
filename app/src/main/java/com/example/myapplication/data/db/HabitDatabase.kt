package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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


    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}