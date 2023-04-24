package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.DailyGoal

interface GetDailyGoalUseCase {

    fun execute(meal: Meal, dailyGoal: DailyGoal): DailyGoal
}
