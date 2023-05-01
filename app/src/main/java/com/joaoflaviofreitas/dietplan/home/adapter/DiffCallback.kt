package com.joaoflaviofreitas.dietplan.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

class DiffCallback: DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
        oldItem.uid == newItem.uid

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal) =
        oldItem == newItem
}
