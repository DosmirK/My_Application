package com.example.myapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HabitModel(
    val id: Int,
    val name: String,
    var isCompleted: Boolean = false
): Parcelable