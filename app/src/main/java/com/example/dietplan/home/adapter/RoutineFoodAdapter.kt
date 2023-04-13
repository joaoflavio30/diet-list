package com.example.dietplan.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dietplan.data.local.Meal
import com.example.dietplan.databinding.ListFoodRvBinding
import com.example.dietplan.extensions.formatToTwoHouses
import com.example.dietplan.extensions.toTimeString
import java.time.LocalDateTime

class RoutineFoodAdapter: ListAdapter<Meal, RoutineFoodAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ListFoodRvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Meal) = with(binding) {
            foodName.text = currentItem.foodName
            calorie.text = currentItem.calories.formatToTwoHouses().toString()
            time.text = LocalDateTime.now().toTimeString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFoodRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}
