package com.joaoflaviofreitas.dietplan.component.food.domain.model

data class TotalDaily(
    val enercKcal: EnercKcal,
    val fat: Fat,
    val carb: Carb,
    val fiber: Fiber,
    val sugar: Sugar,
    val protein: Protein
)
