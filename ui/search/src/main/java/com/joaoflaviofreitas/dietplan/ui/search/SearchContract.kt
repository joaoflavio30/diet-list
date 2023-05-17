package com.joaoflaviofreitas.dietplan.ui.search

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood

interface SearchContract {
    interface SearchFragment {
        fun setOnClickListener()

        fun checkFields(): Boolean

        fun showToastLengthLong(text: String)

        fun progressBarObserver()

        fun observeDailyDiet()

        fun setNutrientsNumber()

        fun bindData()

        fun navigateToHomeFragment()

        fun saveAchievedGoal()
    }
    interface SearchViewModel {
        suspend fun isPossibleToFetchDataOffline(ingredient: RequestFood): Boolean

        fun getMeal(ingredient: RequestFood)

        fun incrementNutrientsToDailyDiet(meal: Meal, dailyGoal: DailyGoal)

        fun resetDailyDietWhenMidnight()

        fun saveRemoteDataInDatabase(meal: Meal)

        suspend fun incWater(userEmail: String): Boolean

        fun submitDailyDiet(nutrients: DailyGoal)

        suspend fun getDailyDiet(userEmail: String): DailyGoal

        fun submitAchievedGoal(achievedGoal: AchievedGoal)

        suspend fun getAchievedGoal(userEmail: String): AchievedGoal

        fun updateAchievedGoal(achievedGoal: AchievedGoal)
    }
}
