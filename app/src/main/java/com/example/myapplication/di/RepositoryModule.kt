package com.example.myapplication.di

import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.data.db.dao.HabitDataDao
import com.example.myapplication.data.repositories.dayrepository.AnalyticsRepositoryImpl
import com.example.myapplication.data.repositories.dayrepository.DayReadOnlyRepositoryImpl
import com.example.myapplication.data.repositories.dayrepository.DayWriteRepositoryImpl
import com.example.myapplication.data.repositories.dayrepository.ProgressRepositoryImpl
import com.example.myapplication.data.repositories.habitrepository.HabitResetRepositoryImpl
import com.example.myapplication.data.repositories.habitrepository.HabitStatsRepositoryImpl
import com.example.myapplication.data.repositories.habitrepository.ReadOnlyHabitRepositoryImpl
import com.example.myapplication.data.repositories.habitrepository.WriteHabitRepositoryImpl
import com.example.myapplication.domain.repositories.dayrepository.AnalyticsRepository
import com.example.myapplication.domain.repositories.dayrepository.DayReadOnlyRepository
import com.example.myapplication.domain.repositories.dayrepository.DayWriteRepository
import com.example.myapplication.domain.repositories.dayrepository.ProgressRepository
import com.example.myapplication.domain.repositories.habitrepository.HabitResetRepository
import com.example.myapplication.domain.repositories.habitrepository.HabitStatsRepository
import com.example.myapplication.domain.repositories.habitrepository.ReadOnlyHabitRepository
import com.example.myapplication.domain.repositories.habitrepository.WriteHabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDayReadOnlyRepository(
        dao: DayDataDao
    ): DayReadOnlyRepository {
        return DayReadOnlyRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideDayWriteRepository(
        dao: DayDataDao
    ): DayWriteRepository {
        return DayWriteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideProgressRepository(
        dao: DayDataDao
    ): ProgressRepository {
        return ProgressRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideHabitResetRepository(
        dao: HabitDataDao
    ): HabitResetRepository {
        return HabitResetRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideHabitStatsRepository(
        dao: HabitDataDao
    ): HabitStatsRepository {
        return HabitStatsRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideReadOnlyHabitRepository(
        dao: HabitDataDao
    ): ReadOnlyHabitRepository {
        return ReadOnlyHabitRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideWriteHabitRepository(
        dao: HabitDataDao
    ): WriteHabitRepository {
        return WriteHabitRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        dao:DayDataDao
    ): AnalyticsRepository {
        return AnalyticsRepositoryImpl(dao)
    }


}