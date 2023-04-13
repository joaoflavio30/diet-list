package com.example.dietplan.data.model

data class Ingredients(
    val text: String,
    val parsed: List<com.example.dietplan.data.model.Parsed>
)
