package com.example.dietplan.searchfood.usecases

import android.util.Log
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.RequestFood
import com.example.dietplan.repositories.MealRepository
import javax.inject.Inject

class GetMealByDatabaseUseCaseImpl @Inject constructor(private val repository: MealRepository): GetMealByDatabaseUseCase {
    override suspend fun execute(ingredient: RequestFood): Meal? {
        val body = repository.getNutrientsByDatabase(ingredient)
        Log.d("tag321","$body")
        if (body != null) {
            return Meal(foodName = ingredient.foodName, measure = ingredient.measure, protein = body.protein, carb = body.carb, fat = body.fat, quantity = body.quantity, calories = body.calories)
        } else {
            return null
        }
    }
}
