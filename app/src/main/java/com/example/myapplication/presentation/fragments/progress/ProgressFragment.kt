package com.example.myapplication.presentation.fragments.progress

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentProgressBinding
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.presentation.adapters.CalendarAdapter
import com.example.myapplication.presentation.viewmodels.DayDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DayDataViewModel by viewModels()
    private var selectedDate: LocalDate = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchHabitsProgress(selectedDate)

        binding.btnPrev.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            viewModel.fetchHabitsProgress(selectedDate)
        }

        binding.btnNext.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            viewModel.fetchHabitsProgress(selectedDate)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.habitsProgress.collect { progressData ->
                val habitDays = mapToDayModelList(progressData)
                setMonthView(habitDays)
                calculateCompletionPercentage(habitDays)
            }
        }

    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun calculateCompletionPercentage(days: List<DayModel>) {

        val totalDays = days.size.toFloat()

        val completedDays = days.count { it.isCompleted }
        val completionPercentage = (completedDays * 100) / totalDays
        Log.d("ProgressFragment", "Days: ${days.count {it.isCompleted}} \n $completionPercentage \n $totalDays")
        val formattedPercentage = String.format("%.2f", completionPercentage)
        if (completionPercentage >= 80) {
            binding.tvProgressProcent.setTextColor(Color.GREEN)
        } else {
            binding.tvProgressProcent.setTextColor(Color.RED)
        }

        if (completionPercentage.isNaN()){
            binding.tvProgressProcent.text = "Нет данных по этому месяцу."
        }else{
            binding.tvProgressProcent.text = "Выполнено за месяц: $formattedPercentage%"
        }

    }

    private fun mapToDayModelList(progressData: Map<String, Boolean>): List<DayModel> {
        return progressData.map { (date, isCompleted) ->
            DayModel(date = date, isCompleted = isCompleted)
        }
    }

    private fun setMonthView(habitDays: List<DayModel> = emptyList()) {
        binding.monthYearTV.text = monthYearFromDate(selectedDate)
        val daysInMonth: ArrayList<String> = daysInMonthArray(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, habitDays, selectedDate)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)

        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = date.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}