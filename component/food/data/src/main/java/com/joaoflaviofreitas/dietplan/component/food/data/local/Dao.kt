package com.joaoflaviofreitas.dietplan.component.food.data.local

import androidx.room.* // ktlint-disable no-wildcard-imports
import androidx.room.Dao
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

@Dao
interface Dao {

    @Query("SELECT * FROM meal")
    fun getAll(): List<Meal>

    @Query("SELECT * FROM meal WHERE uid IN (:mealIds)")
    fun loadAllByIds(mealIds: IntArray): List<Meal>

    @Query(
        "SELECT * FROM meal WHERE food_name IS :foodName",
    )
    suspend fun findByName(foodName: String): Meal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg meals: Meal)

    @Delete
    fun delete(meal: Meal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyGoal(vararg dailyGoal: DailyGoal)

    @Query("SELECT * FROM daily_goal WHERE user_email = :userEmail")
    suspend fun getDailyGoal(userEmail: String): DailyGoal?

    @Update
    suspend fun updateDailyGoal(dailyGoal: DailyGoal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievedGoal(vararg achievedGoal: AchievedGoal)

    @Query("SELECT * FROM achieved_goal WHERE user_email = :userEmail")
    suspend fun getAchievedGoal(userEmail: String): AchievedGoal

    @Update
    suspend fun updateAchievedGoal(achievedGoal: AchievedGoal)

    @Query("SELECT COUNT(*) FROM daily_goal WHERE user_email = :userEmail")
    fun existsByEmail(userEmail: String): Boolean
}
