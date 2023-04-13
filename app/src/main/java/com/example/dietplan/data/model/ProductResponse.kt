package com.example.dietplan.data.model

data class ProductResponse(
    val uri: String,
    val calories: Double,
    val totalWeight: Double,
    val dietLabels: List<String>,
    val healthLabels: List<String>,
    val cautions: List<String>,
    val totalNutrients: Map<String, com.example.dietplan.data.model.Nutrient>,
    val totalDaily: Map<String, com.example.dietplan.data.model.Nutrient>,
    val ingredients: List<com.example.dietplan.data.model.Ingredients>
)
