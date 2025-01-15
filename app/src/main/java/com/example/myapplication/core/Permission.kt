package com.example.myapplication.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.core.notification.scheduleNotificationWork

fun showNotificationPermissionDialog(activity: Activity) {
    AlertDialog.Builder(activity)
        .setTitle("Разрешение на показ уведомлений")
        .setMessage("Для показа уведомлений нужно разрешение.")
        .setPositiveButton("Разрешить") { _, _ ->
            requestNotificationAccess(activity)
            scheduleNotificationWork(activity)
            Log.e("ololo", "Запуск задачи из диолого")
        }
        .setNegativeButton("Отмена") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun checkNotificationPermission(context: Context): Boolean {
    val notificationManager = NotificationManagerCompat.from(context)
    return notificationManager.areNotificationsEnabled()
}

fun requestNotificationAccess(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        val intent = Intent()
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
        activity.startActivity(intent)
    } else {
        AlertDialog.Builder(activity)
            .setTitle("Разрешение на показ уведомлений")
            .setMessage("Выша версия Android не поддерживает автоматический запрос разрешения." + "\n" +
                    "Пожалуйста дайте разрешение в ручную.")
            .setPositiveButton("Ок") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}