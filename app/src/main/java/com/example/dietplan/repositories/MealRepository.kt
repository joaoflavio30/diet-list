package com.example.dietplan.repositories

import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.ProductResponse
import com.example.dietplan.data.model.RequestFood
import retrofit2.Response

interface MealRepository {

    suspend fun getAll(): List<Meal>

    suspend fun getNutrientsByApi(ingredient: RequestFood): Response<ProductResponse>

    suspend fun findByName(foodName: String): Meal?

    suspend fun getNutrientsByDatabase(ingredient: RequestFood): Meal?

    suspend fun saveMealInDatabase(meal: Meal)
}
