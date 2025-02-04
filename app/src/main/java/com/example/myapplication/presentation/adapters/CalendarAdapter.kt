package com.example.myapplication.presentation.adapters

import android.graphics.Color
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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_GREEN = 1
    private val TYPE_OTHER = 2

    inner class GreenDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    }

    inner class OtherDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    }

    override fun getItemViewType(position: Int): Int {
        val day = daysOfMonth[position]
        return if (day.isNotEmpty()) {
            val date = LocalDate.of(selectedDate.year, selectedDate.month, day.toInt())
            val habitDay = habitDays.find { LocalDate.parse(it.date) == date }
            val today = LocalDate.now()

            if (date.isBefore(today) || date.isEqual(today)) {
                when (habitDay?.isCompleted) {
                    in 50..100-> TYPE_GREEN
                    else -> TYPE_OTHER
                }
            } else {
                TYPE_OTHER
            }
        } else {
            TYPE_OTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_GREEN) {
            val view: View = inflater.inflate(R.layout.item_calendar_cell, parent, false)
            val layoutParams = view.layoutParams
            layoutParams.height = (parent.height * 0.111111111).toInt()
            GreenDayViewHolder(view)
        } else {
            val view: View = inflater.inflate(R.layout.item_calendar_cell_other, parent, false)
            val layoutParams = view.layoutParams
            layoutParams.height = (parent.height * 0.111111111).toInt()
            OtherDayViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val day = daysOfMonth[position]
        if (day.isNotEmpty()) {
            val date = LocalDate.of(selectedDate.year, selectedDate.month, day.toInt())
            val habitDay = habitDays.find { LocalDate.parse(it.date) == date }
            val today = LocalDate.now()

            if (holder is GreenDayViewHolder) {
                holder.dayOfMonth.text = day
                when (habitDay?.isCompleted) {
                    100 -> {
                        val prevDate = date.minusDays(1)
                        val nextDate = date.plusDays(1)
                        val isPrevCompleted = habitDays.any { LocalDate.parse(it.date) == prevDate && it.isCompleted == 100 }
                        val isNextCompleted = habitDays.any { LocalDate.parse(it.date) == nextDate && it.isCompleted == 100 }

                        when {
                            isPrevCompleted && isNextCompleted -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_green_middle)
                            }
                            isPrevCompleted -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_green_end)
                            }
                            isNextCompleted -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_green_start)
                            }
                            else -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_green_single)
                            }
                        }
                    }
                    in 50..99 -> {
                        val prevDate = date.minusDays(1)
                        val nextDate = date.plusDays(1)
                        val isPrevCompleted = habitDays.any { LocalDate.parse(it.date) == prevDate && it.isCompleted in 50..99 }
                        val isNextCompleted = habitDays.any { LocalDate.parse(it.date) == nextDate && it.isCompleted in 50..99 }

                        when {
                            isPrevCompleted && isNextCompleted -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_yellow_middle)
                            }
                            isPrevCompleted -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_yellow_end)
                            }
                            isNextCompleted -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_yellow_start)
                            }
                            else -> {
                                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_yellow_single)
                            }
                        }
                    }
                    0, null -> {
                        holder.dayOfMonth.setBackgroundResource(R.drawable.bg_missed_day)
                    }
                }
            } else if (holder is OtherDayViewHolder) {
                holder.dayOfMonth.text = day
                if (date.isBefore(today) || date.isEqual(today)) {
                    holder.dayOfMonth.setBackgroundResource(R.drawable.bg_missed_day)
                } else {
                    holder.dayOfMonth.setBackgroundResource(R.drawable.bg_other_days)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}