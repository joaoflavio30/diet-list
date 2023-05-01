package com.joaoflaviofreitas.dietplan.component.food.domain.model

data class Parsed(
    val quantity: Double,
    val measure: String,
    val foodMatch: String,
    val weight: Double
)
