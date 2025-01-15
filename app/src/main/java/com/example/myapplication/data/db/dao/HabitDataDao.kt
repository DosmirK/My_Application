package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.db.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHabit(habit: Habit)

    @Delete
    suspend fun remove(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Query("UPDATE habits SET isCompleted = 0")
    suspend fun resetAllHabits()

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT COUNT(*) FROM habits")
    fun getTotalHabitsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM habits WHERE isCompleted = 1")
    fun getCompletedHabitsCount(): Flow<Int>
}
