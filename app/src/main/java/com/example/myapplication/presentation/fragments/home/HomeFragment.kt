package com.example.myapplication.presentation.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.domain.model.HabitModel
import com.example.myapplication.core.state.UiState
import com.example.myapplication.core.utils.Extensions.gone
import com.example.myapplication.core.utils.Extensions.showToast
import com.example.myapplication.core.utils.Extensions.visible
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

    private val habitStatsVM: HabitStatsViewModel by viewModels()
    private val habitViewModel: WriteHabitViewModel by viewModels()
    private val getAllHabitVM: GetAllHabitViewModel by viewModels()
    private val resetHabitVM: ResetHabitViewModel by viewModels()
    private val viewModelDay: DayDataViewModel by viewModels()

    private val habitAdapter by lazy {
        HabitsAdapter(
            itemUpdated = this::onHabitUpdated,
            clickIsChecked = this::onHabitCompletionChanged
        )
    }

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

        resetHabitsIfDateChanged()
        setupUI()
        observeViewModels()
    }

    private fun resetHabitsIfDateChanged() {
        if (resetHabitVM.isDateChanged()) {
            resetHabitVM.resetAllHabits()
        }
    }

    private fun setupUI() {
        with(binding) {
            rvHabit.adapter = habitAdapter
            btnAddHabit.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToHabitFragment())
            }
        }
    }

    private fun observeViewModels() {
        observeHabits()
        observeProgress()
    }

    private fun observeHabits() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getAllHabitVM.habits.collect { state ->
                    handleUiState(
                        state = state,
                        onSuccess = { habits -> habitAdapter.submitList(habits) },
                        loadingView = binding.animLoading
                    )
                }
            }
        }
    }

    private fun observeProgress() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                habitStatsVM.percentageHabitsCompleted().collect { progress ->
                    updateProgressUI(progress)
                    saveProgressDay(progress)
                }
            }
        }
    }

    private fun onHabitUpdated(habit: HabitModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToHabitEditFragment(habit)
        )
    }

    private fun onHabitCompletionChanged(habit: HabitModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("ololo", "привычка: $habit")
            habitViewModel.updateHabit(habit)
            getAllHabitVM.refreshHabits()
            updateProgressAfterHabitChange()
        }
    }

    private suspend fun updateProgressAfterHabitChange() {
        habitStatsVM.percentageHabitsCompleted().collect { progress ->
            updateProgressUI(progress)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateProgressUI(progress: Int) {
        with(binding) {
            progressBar.progress = progress
            progressText.text = "$progress%"
        }
    }

    private fun saveProgressDay(progress: Int) {
        val isCompleted = progress == 100
        val newDayData = DayModel(
            date = LocalDate.now().toString(),
            isCompleted = isCompleted
        )
        viewModelDay.saveHabitDay(newDayData)
    }

    private fun <T> handleUiState(
        state: UiState<T>,
        onSuccess: (T) -> Unit,
        loadingView: View
    ) {
        when (state) {
            is UiState.Loading -> loadingView.visible()
            is UiState.Success -> {
                loadingView.gone()
                state.data?.let { onSuccess(it) }
            }
            is UiState.Empty -> showToast("Data is empty")
            is UiState.Error -> showToast(state.message ?: "Unknown error")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}