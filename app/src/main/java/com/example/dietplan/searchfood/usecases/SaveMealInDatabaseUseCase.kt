package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.local.Meal

interface SaveMealInDatabaseUseCase {

    suspend fun execute(meal: Meal)
}
