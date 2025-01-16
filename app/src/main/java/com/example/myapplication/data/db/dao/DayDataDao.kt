package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.db.model.DayData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DayDataDao {

    @Query("SELECT * FROM dayData WHERE date = :date")
    fun getDayByDate(date: String): Flow<DayData?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfNotExists(habitDay: DayData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(habitDay: DayData)

    @Query("SELECT * FROM dayData")
    fun getAllHabitDays(): Flow<List<DayData>>

    @Query("SELECT * FROM dayData WHERE date BETWEEN :startDate AND :endDate")
    fun getProgressBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<DayData>>
}
