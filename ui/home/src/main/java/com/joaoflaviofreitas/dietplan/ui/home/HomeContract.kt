package com.joaoflaviofreitas.dietplan.ui.home

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal

interface HomeContract {

    interface HomeFragment {
        fun setOnClickListener()

        fun navigateToSearchFragment()

        fun bindData()

        fun showWaterDialog(achievedGoal: AchievedGoal, dailyGoal: DailyGoal)

        fun viewWaterMetrics(achievedGoal: AchievedGoal, dailyGoal: DailyGoal)

        fun bindCurrentDate()

        fun bindProfileImage()

        fun checkIfIsNextDayForZeroAchievedGoal()

        fun showToastLengthLong(text: String)

        fun showAerobicDialog()

        fun viewAerobicMetrics(achievedGoal: AchievedGoal)

        fun showAdjustDietDialog()

        fun navigateToDailyGoalFragment()

        fun achievedGoalObserver()

        fun bindNutritionImageColor()
    }
    interface HomeViewModel {
        suspend fun getAchievedGoal()

        suspend fun getDailyDiet()

        suspend fun incWater(achievedGoal: AchievedGoal): Boolean

        suspend fun resetAchievedGoal(currentDate: String, achievedGoal: AchievedGoal)

        fun resetDailyGoal()

        suspend fun addAerobicAsDone(): Boolean
    }
}
