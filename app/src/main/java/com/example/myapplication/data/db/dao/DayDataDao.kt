package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.db.model.DayData
import java.time.LocalDate

@Dao
interface DayDataDao {

    @Query("SELECT * FROM dayData WHERE date = :date")
    suspend fun getDayByDate(date: String): DayData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(habitDay: DayData)

    @Query("SELECT * FROM dayData WHERE date = :date")
    suspend fun getHabitDayByDate(date: String): DayData?

    @Query("SELECT * FROM dayData")
    suspend fun getAllHabitDays(): List<DayData>

    @Query("SELECT * FROM dayData WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getProgressBetweenDates(startDate: LocalDate, endDate: LocalDate): List<DayData>
}