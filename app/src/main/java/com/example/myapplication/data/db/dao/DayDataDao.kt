package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.db.model.DayData

@Dao
interface DayDataDao {

    @Query("SELECT * FROM dayData WHERE strftime('%Y-%m', date) = :month")
    suspend fun getProgressDataForMonth(month: String): List<DayData>

    @Insert
    suspend fun addDayData(dayData: DayData)

}