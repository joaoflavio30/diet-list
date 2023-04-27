package com.example.dietplan.home

import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.domain.model.DataState

interface HomeContract {

    interface HomeFragment {
        fun setOnClickListener()

        fun navigateToSearchFragment()

        fun bindData()

        fun showDialog()

        fun viewWaterMetrics()

        fun onClickMenuItem()

        fun loadImage()
    }
    interface HomeViewModel {
        suspend fun getAchievedGoal(): AchievedGoal

        suspend fun getDailyDiet(): DailyGoal

        suspend fun incWater(): DataState<Boolean>
    }
}
