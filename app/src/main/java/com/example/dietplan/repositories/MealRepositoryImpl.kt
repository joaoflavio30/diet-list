package com.example.dietplan.repositories

import android.util.Log
import com.example.dietplan.DataState
import com.example.dietplan.data.local.Dao
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.data.model.ProductResponse
import com.example.dietplan.data.model.RequestFood
import com.example.dietplan.data.retrofit.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(private val database: Dao, private val remoteData: Api): MealRepository {
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

    override suspend fun saveDailyGoal(dailyGoal: DailyGoal): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                database.insertDailyGoal(dailyGoal)
                DataState.Success(true)
            } catch (e: Exception) { DataState.Error(e) }
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

    override suspend fun updateDailyGoal(dailyGoal: DailyGoal): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                database.updateDailyGoal(dailyGoal)
                DataState.Success(true)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }

    override suspend fun saveAchievedGoal(achievedGoal: AchievedGoal): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                database.insertAchievedGoal(achievedGoal)
                DataState.Success(true)
            } catch (e: Exception) { DataState.Error(e) }
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

    override suspend fun updateAchievedGoal(achievedGoal: AchievedGoal): DataState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                database.updateAchievedGoal(achievedGoal)
                DataState.Success(true)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }
}
