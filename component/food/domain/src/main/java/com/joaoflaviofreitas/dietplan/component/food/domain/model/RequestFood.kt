package com.joaoflaviofreitas.dietplan.component.food.domain.model

data class RequestFood(
    val foodName: String,
    val quantity: Double,
    val measure: String,
)
