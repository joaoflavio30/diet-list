package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.local.Meal

interface SaveNutrientsFoodInMealUseCase {

    suspend fun execute():Meal
}
