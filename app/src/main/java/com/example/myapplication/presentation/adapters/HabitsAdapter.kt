package com.example.myapplication.presentation.adapters

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemHabitBinding
import com.example.myapplication.domain.model.HabitModel

class HabitsAdapter (
    private val itemUpdated: (HabitModel) -> Unit,
    private val clickIsChecked: (HabitModel) -> Unit
): ListAdapter<HabitModel, HabitsAdapter.HabitViewHolder>(
    object : DiffUtil.ItemCallback<HabitModel>(){
        override fun areItemsTheSame(oldItem: HabitModel, newItem: HabitModel)
                = oldItem == newItem
        override fun areContentsTheSame(oldItem: HabitModel, newItem: HabitModel)
                = oldItem.id == newItem.id

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            itemUpdated(getItem(position))
        }
    }

    inner class HabitViewHolder(
        private val binding: ItemHabitBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(habit: HabitModel) {
            binding.apply {
                cbDone.isChecked = habit.isCompleted
                Log.e("ololo", "updateCB: ${habit.isCompleted}")
                if (habit.isCompleted) {
                    val styledText: CharSequence = Html.fromHtml("<s>${habit.name}</s>", 1)
                    tvHabit.text = styledText
                } else {
                    tvHabit.text = habit.name
                }

                cbDone.setOnCheckedChangeListener { _, isChecked ->
                    clickIsChecked(habit.copy(id = habit.id, isCompleted = isChecked))
                    Log.e("ololo", "updateAdapter: $isChecked")
                    if (isChecked) {
                        val styledText: CharSequence = Html.fromHtml("<s>${habit.name}</s>", 1)
                        tvHabit.text = styledText
                    } else {
                        tvHabit.text = habit.name
                    }
                }
            }
        }
    }
}