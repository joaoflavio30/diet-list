package com.example.dietplan.repositories

import com.example.dietplan.data.local.Dao
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.retrofit.Api
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api, private val dao: Dao) {
    suspend fun getNutrients(ingr: String) = api.getFoodInfo(ingr)

    suspend fun getAll(): List<Meal> = dao.getAll()

    suspend fun insertAll(meals:Meal) = dao.insertAll(meals)

    suspend fun findByName(foodName: String) = dao.findByName(foodName)
}
