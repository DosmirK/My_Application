package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.db.HabitDatabase
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.data.db.dao.HabitDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDataBase(
        @ApplicationContext context: Context
    ): HabitDatabase = Room.databaseBuilder(
        context, HabitDatabase::class.java, "habit"
    ).build()

    @Singleton
    @Provides
    fun provideHabitDao(habitDatabase: HabitDatabase): HabitDataDao =
        habitDatabase.getHabitDao()

    @Singleton
    @Provides
    fun provideDayDataDao(habitDatabase: HabitDatabase): DayDataDao =
        habitDatabase.getDayDataDao()

}