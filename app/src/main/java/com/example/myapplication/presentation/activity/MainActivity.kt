package com.example.myapplication.presentation.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.core.checkNotificationPermission
import com.example.myapplication.core.notification.scheduleNotificationWork
import com.example.myapplication.core.showNotificationPermissionDialog
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            window.insetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
            window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_green)
        } else {
            @Suppress("DEPRECATION")
            window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_green)
        }

        if (!checkNotificationPermission(this)) {
            showNotificationPermissionDialog(this)
        } else {
            scheduleNotificationWork(this)
            Log.e("ololo", "Запуск задачи")
        }

    }
}