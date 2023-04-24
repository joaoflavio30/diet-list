package com.example.dietplan.searchfood.usecases

import android.util.Log
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.RequestFood
import com.example.dietplan.extensions.formatToTwoHouses
import com.example.dietplan.repositories.MealRepository
import javax.inject.Inject

class GetMealByApiUseCaseImpl @Inject constructor(private val repository: MealRepository): GetMealByApiUseCase {
    override suspend fun execute(ingredient: RequestFood): Meal? {
        val body = repository.getNutrientsByApi(ingredient).body()
        Log.d("tag123", "$body")
        if (body != null && body.calories != 0.0) {
            return Meal(
                foodName = ingredient.foodName,
                measure = ingredient.measure,
                protein = body.totalNutrients["PROCNT"]!!.quantity.formatToTwoHouses(),
                carb =
                body.totalNutrients["CHOCDF"]!!.quantity.formatToTwoHouses(),
                fat = body.totalNutrients["FAT"]!!.quantity.formatToTwoHouses(),
                quantity = ingredient.quantity,
                calories = body.calories,
            )
        } else {
            return null
        }
    }
}
