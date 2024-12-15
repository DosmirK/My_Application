package com.example.myapplication.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.model.DayModel
import java.time.LocalDate

class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
    private val habitDays: List<DayModel>,
    private val selectedDate: LocalDate
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day = daysOfMonth[position]
        holder.dayOfMonth.text = day

        if (day.isNotEmpty()) {
            val date = LocalDate.of(selectedDate.year, selectedDate.month, day.toInt())
            val habitDay = habitDays.find { it.date == date.toString() }
            Log.e("ololo", "habitDay: $habitDay")
            when {
                habitDay?.isCompleted == true -> {
                    holder.dayOfMonth.setBackgroundResource(R.drawable.bg_complited_day)
                }
                habitDay?.isCompleted == false -> {
                    holder.dayOfMonth.setBackgroundResource(R.drawable.bg_missed_day)
                }
                else -> {
                    holder.dayOfMonth.setBackgroundResource(R.drawable.bg_other_days)
                }
            }
        } else {
            holder.dayOfMonth.setBackgroundResource(0)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}