package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.RequestFood

interface GetMealByApiUseCase {

    suspend fun execute(ingredient: RequestFood): Meal?
}
