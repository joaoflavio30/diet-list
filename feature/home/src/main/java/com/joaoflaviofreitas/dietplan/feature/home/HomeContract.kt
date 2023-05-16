package com.joaoflaviofreitas.dietplan.feature.home

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal

interface HomeContract {

    interface HomeFragment {
        fun setOnClickListener()

        fun navigateToSearchFragment()

        fun bindData()

        fun showDialog()

        fun viewWaterMetrics()

        fun bindCurrentDate()

        fun bindProfileImage()

        fun checkIfIsNextDayForZeroAchievedGoal()

        fun showToastLengthLong(text: String)
    }
    interface HomeViewModel {
        suspend fun getAchievedGoal(userEmail: String): AchievedGoal

        suspend fun getDailyDiet(userEmail: String): DailyGoal

        suspend fun incWater(userEmail: String): Boolean

        fun resetAchievedGoal(userEmail: String)
    }
}
