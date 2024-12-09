package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.db.dao.DayDataDao
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.repositories.DayDataRepository
import com.example.myapplication.domain.utils.DataState
import com.example.myapplication.domain.utils.convertors.mapToDayModel
import com.example.myapplication.domain.utils.convertors.toDayData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DayDataRepositoryImpl @Inject constructor(
    private val dayDataDao: DayDataDao
): DayDataRepository {
    override suspend fun getProgressDataForMonth(month: String): Flow<DataState<List<DayModel>>> =
        flow {
            emit(DataState.Loading())
            try {
                val data = dayDataDao.getProgressDataForMonth(month).mapToDayModel()
                emit(DataState.Success(data))
                Log.e("ololo", "dataRepost: $data")
            } catch (e: Exception) {
                emit(DataState.Error(e.localizedMessage))
                Log.e("ololo", "errorRepost: ${e.localizedMessage}")
            }
        }

    override suspend fun addHabit(dayData: DayModel) =
        dayDataDao.addDayData(dayData.toDayData())
}