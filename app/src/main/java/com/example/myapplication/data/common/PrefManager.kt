package com.example.myapplication.data.common

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_BOOLEAN = "key_boolean"
    }

    // Метод для сохранения булевого значения
    fun saveBoolean(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_BOOLEAN, value)
        editor.apply()
    }

    // Метод для извлечения булевого значения
    fun getBoolean(): Boolean {
        return sharedPreferences.getBoolean(KEY_BOOLEAN, false) // Значение по умолчанию false
    }
}