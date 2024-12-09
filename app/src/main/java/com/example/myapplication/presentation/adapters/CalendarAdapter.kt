package com.example.myapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.common.PrefManager
import java.time.LocalDate

class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
    private val onItemListener: OnItemListener,
    private val selectedDate: LocalDate,
    private val prefManager: PrefManager
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)

        init {
            itemView.setOnClickListener {
                val dayText = dayOfMonth.text.toString()
                // Разрешаем выбор только текущей даты
                if (dayText == selectedDate.dayOfMonth.toString()) {
                    onItemListener.onItemClick(adapterPosition, dayText)
                }
            }
        }
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

        val isToday = day == selectedDate.dayOfMonth.toString() &&
                selectedDate.monthValue == LocalDate.now().monthValue &&
                selectedDate.year == LocalDate.now().year

        if (isToday) {
            val isChecked = prefManager.getBoolean()
            if (isChecked){
                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_complited_day)
            }else{
                holder.dayOfMonth.setBackgroundResource(R.drawable.bg_missed_day)
            }
        } else if (day.isNotEmpty()) {
            holder.dayOfMonth.setBackgroundResource(R.drawable.bg_other_days)
        } else {
            holder.dayOfMonth.setBackgroundResource(0)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}