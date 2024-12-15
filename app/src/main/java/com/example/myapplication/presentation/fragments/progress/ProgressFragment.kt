package com.example.myapplication.presentation.fragments.progress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.common.PrefManager
import com.example.myapplication.databinding.FragmentProgressBinding
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.presentation.adapters.CalendarAdapter
import com.example.myapplication.presentation.viewmodels.DayDataViewModel
import com.example.myapplication.presentation.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DayDataViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private var selectedDate: LocalDate = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPrev.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            viewModel.fetchHabitsProgress(selectedDate)
        }

        binding.btnNext.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            viewModel.fetchHabitsProgress(selectedDate)
        }

        viewModel.habitsProgress.observe(viewLifecycleOwner) { progressData ->
            val habitDays = mapToDayModelList(progressData)
            setMonthView(habitDays)
        }

        sharedViewModel.habitProgress.observe(viewLifecycleOwner) { progressData ->
            setMonthView(progressData)
        }

        viewModel.fetchHabitsProgress(selectedDate)
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