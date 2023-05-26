package com.joaoflaviofreitas.dietplan.component.food.domain.repository

import com.joaoflaviofreitas.dietplan.component.food.domain.model.* // ktlint-disable no-wildcard-imports
import retrofit2.Response

interface MealRepository {

    suspend fun getAll(): List<Meal>

    suspend fun getNutrientsByApi(ingredient: RequestFood): Response<ProductResponse>

    suspend fun findByName(foodName: String): Meal?

    suspend fun getNutrientsByDatabase(ingredient: RequestFood): Meal?

    suspend fun saveMealInDatabase(meal: Meal)

    suspend fun saveDailyGoal(dailyGoal: DailyGoal): Boolean

    suspend fun getDailyGoal(userEmail: String): DailyGoal?

    suspend fun updateDailyGoal(dailyGoal: DailyGoal): Boolean

    suspend fun saveAchievedGoal(achievedGoal: AchievedGoal): Boolean

    suspend fun getAchievedGoal(userEmail: String): AchievedGoal

    suspend fun updateAchievedGoal(achievedGoal: AchievedGoal): Boolean

    suspend fun dailyGoalExistsByEmail(userEmail: String): Boolean

    suspend fun achievedGoalExistsByEmail(userEmail: String): Boolean

    suspend fun resetDailyGoal(userEmail: String)
}
