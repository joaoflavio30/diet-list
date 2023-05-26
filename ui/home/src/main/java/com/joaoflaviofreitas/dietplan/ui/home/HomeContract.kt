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
    }
    interface HomeViewModel {
        suspend fun getAchievedGoal(userEmail: String)

        suspend fun getDailyDiet(userEmail: String)

        suspend fun incWater(userEmail: String, achievedGoal: AchievedGoal): Boolean

        suspend fun resetAchievedGoal(userEmail: String, currentDate: String, achievedGoal: AchievedGoal)

        fun resetDailyGoal(userEmail: String)

        suspend fun addAerobicAsDone(userEmail: String): Boolean
    }
}
