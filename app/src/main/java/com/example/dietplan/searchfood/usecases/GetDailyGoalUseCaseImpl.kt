package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.DailyGoal

class GetDailyGoalUseCaseImpl: GetDailyGoalUseCase {
    override fun execute(meal: Meal, dailyGoal: DailyGoal): DailyGoal {
        return DailyGoal(
            dailyGoal.calories + meal.calories,
            dailyGoal.protein + meal.protein,
            dailyGoal.carb + meal.carb,
            dailyGoal.fat + meal.fat,
        )
    }
}
