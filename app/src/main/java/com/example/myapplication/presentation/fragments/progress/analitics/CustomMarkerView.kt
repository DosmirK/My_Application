package com.example.myapplication.presentation.fragments.progress.analitics

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.myapplication.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context, private val dateLabels: List<String>) :
    MarkerView(context, R.layout.custom_marker_view) {

    private val markerText: TextView = findViewById(R.id.marker_text)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e is CandleEntry) {
            val index = e.x.toInt()
            if (index in dateLabels.indices) {
                markerText.text = dateLabels[index]
            }
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-width / 2).toFloat(), (-height).toFloat())
    }
}