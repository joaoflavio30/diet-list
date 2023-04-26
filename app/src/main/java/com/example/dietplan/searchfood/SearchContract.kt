package com.example.dietplan.searchfood

import com.example.dietplan.DataState
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.data.model.RequestFood

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

        suspend fun incWater(): DataState<Boolean>

        fun submitDailyDiet(nutrients: DailyGoal)

        suspend fun getDailyDiet(): DailyGoal

        fun submitAchievedGoal(achievedGoal: AchievedGoal)

        suspend fun getAchievedGoal(): AchievedGoal

        fun updateAchievedGoal(achievedGoal: AchievedGoal)
    }
}
