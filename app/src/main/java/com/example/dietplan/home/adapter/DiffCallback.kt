package com.example.dietplan.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.dietplan.data.local.Meal

class DiffCallback: DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
        oldItem.uid == newItem.uid

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal) =
        oldItem == newItem
}
