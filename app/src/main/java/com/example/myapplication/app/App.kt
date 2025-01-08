package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.core.utils.helpers.WorkManagerHelper.scheduleDailyWork
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){

    override fun onCreate() {
        super.onCreate()

        scheduleDailyWork(this)
    }
}