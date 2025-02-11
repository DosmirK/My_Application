package com.example.myapplication.data.common

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class DateManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val preferences = context.getSharedPreferences("date_prefs", Context.MODE_PRIVATE)

    fun isDateChanged(): Boolean {
        val lastSavedDate = preferences.getString(LAST_SAVED_DATE_KEY, null) ?: return true
        val currentDate = LocalDate.now(ZoneOffset.UTC).toString()

        return if (lastSavedDate != currentDate) {
            CoroutineScope(Dispatchers.IO).launch {
                saveCurrentDate(currentDate)
            }
            true
        } else {
            false
        }
    }

    private suspend fun saveCurrentDate(date: String) {
        withContext(Dispatchers.IO) {
            preferences.edit().putString(LAST_SAVED_DATE_KEY, date).apply()
        }
    }

    companion object {
        private const val LAST_SAVED_DATE_KEY = "last_saved_date"
    }
}