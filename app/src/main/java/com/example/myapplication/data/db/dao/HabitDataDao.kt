package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.db.model.Habit

@Dao
interface HabitDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHabit(habit: Habit)

    @Delete
    suspend fun remove(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Query("SELECT * FROM habits")
    suspend fun getAllHabits(): List<Habit>

    @Query("SELECT COUNT(*) FROM habits")
    suspend fun getTotalHabitsCount(): Int

    @Query("SELECT COUNT(*) FROM habits WHERE isCompleted = 1")
    suspend fun getCompletedHabitsCount(): Int

}