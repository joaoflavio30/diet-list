package com.joaoflaviofreitas.dietplan.component.food.data.repository

import android.util.Log
import com.joaoflaviofreitas.dietplan.component.food.data.local.Dao
import com.joaoflaviofreitas.dietplan.component.food.data.remote.Api
import com.joaoflaviofreitas.dietplan.component.food.domain.model.* // ktlint-disable no-wildcard-imports
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(private val database: Dao, private val remoteData: Api):
    MealRepository {
    override suspend fun getAll(): List<Meal> {
        return withContext(Dispatchers.IO) {
            try { database.getAll()
            } catch (e: Exception) {
                throw Exception("Failed to get data from database: ${e.message}")
            }
        }
    }

    override suspend fun getNutrientsByApi(ingredient: RequestFood): Response<ProductResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val parsedIngredient = ingredient.quantity.toString() + " " + ingredient.measure + " " + ingredient.foodName
                Log.d("tag1234", parsedIngredient)
                remoteData.getFoodInfo(parsedIngredient)
            } catch (e: Exception) {
                throw Exception("Failed to get data from API: ${e.message}")
            }
        }
    }

    override suspend fun findByName(foodName: String): Meal? {
        return withContext(Dispatchers.IO) {
            try {
                database.findByName(foodName)
            } catch (e: Exception) {
                throw Exception("Failed to get foodName from database: ${e.message}")
            }
        }
    }

    override suspend fun getNutrientsByDatabase(ingredient: RequestFood): Meal? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("tag", ingredient.foodName)
                database.findByName(ingredient.foodName)
            } catch (e: Exception) {
                throw Exception("Failed to get foodName from database: ${e.message}")
            }
        }
    }

    override suspend fun saveMealInDatabase(meal: Meal) {
        withContext(Dispatchers.IO) {
            try {
                database.insertAll(meal)
            } catch (e: Exception) {
                throw Exception("Failed to save Meal in database: ${e.message}")
            }
        }
    }

    override suspend fun saveDailyGoal(dailyGoal: DailyGoal): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                database.insertDailyGoal(dailyGoal)
                true
            } catch (e: Exception) { false }
        }
    }

    override suspend fun getDailyGoal(): DailyGoal {
        return withContext(Dispatchers.IO) {
            try {
                database.getDailyGoal()
            } catch (e: Exception) {
                throw Exception("Failed to get dailyGoal in database: ${e.message}")
            }
        }
    }

    override suspend fun updateDailyGoal(dailyGoal: DailyGoal): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                database.updateDailyGoal(dailyGoal)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun saveAchievedGoal(achievedGoal: AchievedGoal): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                database.insertAchievedGoal(achievedGoal)
                true
            } catch (e: Exception) { false }
        }
    }

    override suspend fun getAchievedGoal(): AchievedGoal {
        return withContext(Dispatchers.IO) {
            try {
                database.getAchievedGoal()
            } catch (e: Exception) {
                throw Exception("Failed to get achievedGoal in database: ${e.message}")
            }
        }
    }

    override suspend fun updateAchievedGoal(achievedGoal: AchievedGoal): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                database.updateAchievedGoal(achievedGoal)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}
