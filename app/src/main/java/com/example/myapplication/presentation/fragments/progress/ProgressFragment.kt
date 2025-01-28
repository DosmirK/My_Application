package com.example.myapplication.presentation.fragments.progress

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
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
import kotlinx.coroutines.flow.collectLatest
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
            viewModel.fetchHabitsProgress(viewModel.selectedDate.value.minusMonths(1))
        }

        binding.btnNext.setOnClickListener {
            viewModel.fetchHabitsProgress(viewModel.selectedDate.value.plusMonths(1))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.habitsProgress.collectLatest { progressData ->
                val habitDays = if (progressData.isNotEmpty()) {
                    mapToDayModelList(progressData)
                } else {
                    emptyList()
                }
                setMonthView(habitDays)
                calculateCompletionPercentage(habitDays)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedDate.collectLatest {
                setMonthView(emptyList())
            }
        }

    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun calculateCompletionPercentage(days: List<DayModel>) {

        if (days.isEmpty()) {
            binding.tvProgressProcent.text = "Нет данных за этот месяц."
            binding.tvProgressProcent.setTextColor(Color.RED)
            return
        }

        val currentMonth = LocalDate.now().monthValue
        val currentYear = LocalDate.now().year
        val totalDays = YearMonth.of(currentYear, currentMonth).lengthOfMonth().toFloat()

        val completedDays = days.count { it.isCompleted }
        val completionPercentage = (completedDays * 100) / totalDays
        val formattedPercentage = String.format("%.2f", completionPercentage)
        if (completionPercentage >= 80) {
            binding.tvProgressProcent.setTextColor(Color.GREEN)
        } else {
            binding.tvProgressProcent.setTextColor(Color.RED)
        }

        if (completionPercentage.isNaN()) {
            binding.tvProgressProcent.text = "Нет данных по этому месяцу."
        } else {
            binding.tvProgressProcent.text = "Выполнено за месяц: $formattedPercentage%"
        }
    }


    private fun mapToDayModelList(progressData: Map<String, Boolean>): List<DayModel> {
        return progressData.map { (date, isCompleted) ->
            DayModel(date = date, isCompleted = isCompleted)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setMonthView(habitDays: List<DayModel> = emptyList()) {
        binding.monthYearTV.text = monthYearFromDate(viewModel.selectedDate.value)
        val daysInMonth: ArrayList<String> = daysInMonthArray(viewModel.selectedDate.value)

        val calendarAdapter = CalendarAdapter(daysInMonth, habitDays, viewModel.selectedDate.value)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
        calendarAdapter.notifyDataSetChanged()
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