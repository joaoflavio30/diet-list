package com.joaoflaviofreitas.dietplan.component.food.domain.model

data class ProductResponse(
    val uri: String,
    val calories: Double,
    val totalWeight: Double,
    val dietLabels: List<String>,
    val healthLabels: List<String>,
    val cautions: List<String>,
    val totalNutrients: Map<String, Nutrient>,
    val totalDaily: Map<String, Nutrient>,
    val ingredients: List<Ingredients>
)
