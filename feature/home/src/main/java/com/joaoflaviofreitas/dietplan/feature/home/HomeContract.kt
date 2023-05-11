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
    }
    interface HomeViewModel {
        suspend fun getAchievedGoal(): AchievedGoal

        suspend fun getDailyDiet(): DailyGoal

        suspend fun incWater(): Boolean

        fun resetAchievedGoal()
    }
}
