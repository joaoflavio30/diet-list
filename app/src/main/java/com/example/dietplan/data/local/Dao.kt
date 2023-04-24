package com.example.dietplan.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {

    @Query("SELECT * FROM meal")
    fun getAll(): List<com.example.dietplan.data.local.Meal>

    @Query("SELECT * FROM meal WHERE uid IN (:mealIds)")
    fun loadAllByIds(mealIds: IntArray): List<com.example.dietplan.data.local.Meal>

    @Query(
        "SELECT * FROM meal WHERE food_name IS :foodName",
    )
    suspend fun findByName(foodName: String): Meal?

    @Insert
    suspend fun insertAll(vararg meals: Meal)

    @Delete
    fun delete(meal: com.example.dietplan.data.local.Meal)
}
