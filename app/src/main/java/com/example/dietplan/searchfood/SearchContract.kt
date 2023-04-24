package com.example.dietplan.searchfood

import com.example.dietplan.data.local.Meal
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
    }
    interface SearchViewModel {
        suspend fun isPossibleToFetchDataOffline(ingredient: RequestFood): Boolean

        fun getMeal(ingredient: RequestFood)

        fun incrementNutrientsToDailyDiet(meal: Meal, dailyGoal: DailyGoal)

        fun resetDailyDietWhenMidnight()

        fun saveRemoteDataInDatabase(meal: Meal)

        fun incWater()

        fun submitDailyDiet(nutrients: DailyGoal)
    }
}
