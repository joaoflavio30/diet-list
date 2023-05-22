package com.joaoflaviofreitas.dietplan.ui.home

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal

interface HomeContract {

    interface HomeFragment {
        fun setOnClickListener()

        fun navigateToSearchFragment()

        fun bindData()

        fun showWaterDialog()

        fun viewWaterMetrics()

        fun bindCurrentDate()

        fun bindProfileImage()

        fun checkIfIsNextDayForZeroAchievedGoal()

        fun showToastLengthLong(text: String)

        fun showAerobicDialog()

        fun viewAerobicMetrics()
    }
    interface HomeViewModel {
        suspend fun getAchievedGoal(userEmail: String): AchievedGoal

        suspend fun getDailyDiet(userEmail: String): DailyGoal

        suspend fun incWater(userEmail: String): Boolean

        fun resetAchievedGoal(userEmail: String, currentDate: String)

        suspend fun addAerobicAsDone(userEmail: String): Boolean
    }
}
