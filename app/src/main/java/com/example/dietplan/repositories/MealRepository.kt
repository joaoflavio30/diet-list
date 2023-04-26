package com.example.dietplan.repositories

import com.example.dietplan.DataState
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.data.model.ProductResponse
import com.example.dietplan.data.model.RequestFood
import retrofit2.Response

interface MealRepository {

    suspend fun getAll(): List<Meal>

    suspend fun getNutrientsByApi(ingredient: RequestFood): Response<ProductResponse>

    suspend fun findByName(foodName: String): Meal?

    suspend fun getNutrientsByDatabase(ingredient: RequestFood): Meal?

    suspend fun saveMealInDatabase(meal: Meal)

    suspend fun saveDailyGoal(dailyGoal: DailyGoal): DataState<Boolean>

    suspend fun getDailyGoal(): DailyGoal

    suspend fun updateDailyGoal(dailyGoal: DailyGoal): DataState<Boolean>

    suspend fun saveAchievedGoal(achievedGoal: AchievedGoal): DataState<Boolean>

    suspend fun getAchievedGoal(): AchievedGoal

    suspend fun updateAchievedGoal(achievedGoal: AchievedGoal): DataState<Boolean>
}
