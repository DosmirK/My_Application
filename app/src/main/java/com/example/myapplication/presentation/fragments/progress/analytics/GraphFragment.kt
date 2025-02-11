package com.example.myapplication.presentation.fragments.progress.analytics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentGraphBinding
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.domain.model.DayModel
import com.example.myapplication.presentation.viewmodels.dayviewmodel.AnalyticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalyticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCandleStickChart()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.progressData.collectLatest { dayDataList ->
                Log.d("fefer", "полуеный список: $dayDataList")
                if (dayDataList.isNotEmpty()){
                    updateChart(dayDataList)
                    showReviewOptions(dayDataList.takeLast(30))
                }
            }
        }
    }

    private fun setupCandleStickChart() {
        val candleStickChart = binding.candleStickChart

        candleStickChart.apply {
            description.text = "Прогресс до сегодня"
            setPinchZoom(true)
            isDragEnabled = true
            isScaleXEnabled = true
            isScaleYEnabled = false
            isDragXEnabled = true
            setVisibleXRangeMinimum(5f)
            setVisibleXRangeMaximum(10f)
            animateY(1000)
            legend.isEnabled = false
            setDrawGridBackground(false)
        }
    }


    private fun updateChart(dayDataList: List<DayModel>) {
        val candleEntries = mutableListOf<CandleEntry>()
        val dateLabels = mutableListOf<String>()

        var previousClose = 0f

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        dayDataList.forEachIndexed { index, dayData ->
            val isCompleted = dayData.isCompleted

            val (open, close) = when {
                isCompleted == 100 -> previousClose to (previousClose + 1f)
                isCompleted >= 50 -> previousClose to (previousClose + 0.5f)
                isCompleted > 0 -> previousClose to (previousClose - 0.5f)
                else -> previousClose to (previousClose - 1f)
            }

            val shadowHigh = maxOf(open, close) + 0.2f
            val shadowLow = minOf(open, close) - 0.2f

            candleEntries.add(
                CandleEntry(index.toFloat(), shadowHigh, shadowLow, open, close)
            )

            previousClose = close

            val parsedDate = try {
                dateFormat.parse(dayData.date)
            } catch (e: Exception) {
                null
            }

            parsedDate?.let {
                val formattedDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(it)
                dateLabels.add(formattedDate)
            } ?: run {
                dateLabels.add("Некорректная дата")
            }
        }

        val dataSet = CandleDataSet(candleEntries, "Прогресс до сегодня").apply {
            shadowColor = Color.DKGRAY
            shadowWidth = 0.8f
            decreasingColor = Color.RED
            increasingColor = Color.GREEN
            decreasingPaintStyle = Paint.Style.FILL
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = Color.BLUE
            setDrawValues(false)
        }

        val candleData = CandleData(dataSet)
        binding.candleStickChart.apply {
            data = candleData

            xAxis.setDrawLabels(false)

            val markerView = CustomMarkerView(requireContext(), dateLabels)
            markerView.chartView = this
            marker = markerView

            invalidate()
        }
    }

    private fun showReviewOptions(lastData: List<DayModel>) {
        val reviewOptions = when {
            lastData.all { it.isCompleted == 100 } -> "Отличный прогресс! Вы почти завершили."
            lastData.any { it.isCompleted >= 50 } -> "Вы на полпути! Продолжайте работать!"
            lastData.any { it.isCompleted > 0 } -> "Есть прогресс, но нужно ускориться."
            else -> "Небольшой прогресс. Попробуйте улучшить результат!"
        }

        binding.analysisTv.text = reviewOptions
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}