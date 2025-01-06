package com.example.myapplication.presentation.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.data.common.DateManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.core.state.UiState
import com.example.myapplication.presentation.adapters.HabitsAdapter
import com.example.myapplication.presentation.viewmodels.DayDataViewModel
import com.example.myapplication.presentation.viewmodels.habitviewmodel.GetAllHabitViewModel
import com.example.myapplication.presentation.viewmodels.habitviewmodel.HabitStatsViewModel
import com.example.myapplication.presentation.viewmodels.habitviewmodel.ResetHabitViewModel
import com.example.myapplication.presentation.viewmodels.habitviewmodel.WriteHabitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val habitStatsVM : HabitStatsViewModel by viewModels()
    private val habitViewModel : WriteHabitViewModel by viewModels()
    private val getAllHabitVM : GetAllHabitViewModel by  viewModels()
    private val resetHabitVM : ResetHabitViewModel by viewModels()
    private val viewModelDay: DayDataViewModel by viewModels()
    private val habitAdapter = HabitsAdapter(itemUpdated = this::habitUpdate, clickIsChecked = this::clickCheckBox )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAndResetHabits()

        setupData()
        initView()
        initListeners()
        updateProgress()
    }

    private fun checkAndResetHabits() {
        val dateManager = DateManager(requireContext())
        if (dateManager.isDateChanged()) {
            resetHabitVM.resetAllHabits()
            dateManager.saveCurrentDate(LocalDate.now().toString())
        }
    }

    private fun initListeners() {
        binding.btnAddHabit.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToHabitFragment())
        }
    }

    private fun setupData() {
        viewLifecycleOwner.lifecycleScope.launch {
            getAllHabitVM.getAllHabits()
            getAllHabitVM.habits.collect { result ->
                Log.e("ololo", "data: ${result.data}")
                when (result) {
                    is UiState.Loading -> binding.animLoading.visibility = View.VISIBLE

                    is UiState.Success -> {
                        habitAdapter.submitList(result.data)
                        binding.animLoading.visibility = View.GONE
                    }

                    is UiState.Empty -> Toast.makeText(requireContext(), "Data is empty",
                        Toast.LENGTH_LONG
                    ).show()

                    is UiState.Error -> Toast.makeText(requireContext(), result.message ?: "Unknown error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun updateProgress() {
        viewLifecycleOwner.lifecycleScope.launch {
            val progress = habitStatsVM.percentageHabitsCompleted()
            val progressText = "$progress%"
            binding.progressBar.progress = progress
            binding.progressText.text = progressText

            val newDayData = DayModel(
                date = LocalDate.now().toString(),
                isCompleted = progress == 100
            )
            viewModelDay.saveHabitDay(newDayData)

            Log.e("ololo", "progressBar: $progress")
        }
    }

    private fun initView() {
        binding.rvHabit.adapter = habitAdapter
    }
    private fun habitUpdate(habit: HabitModel){
        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToHabitEditFragment(habit))
    }

    private fun clickCheckBox(habit: HabitModel){
        viewLifecycleOwner.lifecycleScope.launch {
            habitViewModel.updateHabit(
                habit.copy(
                    isCompleted = habit.isCompleted,
                    id = habit.id,
                    name = habit.name
                )
            )
            viewLifecycleOwner.lifecycleScope.launch {
                getAllHabitVM.habits.collect { newData ->
                    when (newData) {
                        is UiState.Loading -> binding.animLoading.visibility = View.VISIBLE

                        is UiState.Success -> {
                            habitAdapter.submitList(newData.data)
                            binding.animLoading.visibility = View.GONE
                        }

                        is UiState.Empty -> Toast.makeText(
                            requireContext(), "Data is empty",
                            Toast.LENGTH_LONG
                        ).show()

                        is UiState.Error -> Toast.makeText(
                            requireContext(), newData.message ?: "Unknown error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            Log.e("ololo", "update: $habit")
        }
        updateProgress()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}