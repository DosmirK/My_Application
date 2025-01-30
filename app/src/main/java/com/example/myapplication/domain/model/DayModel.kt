package com.example.myapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayModel(
    val date: String,
    val isCompleted: Int
): Parcelable
