package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

internal class GetDailyGoalUseCaseImpl: GetDailyGoalUseCase {
    override fun execute(meal: Meal, dailyGoal: DailyGoal): DailyGoal {
        return DailyGoal(
            calories = dailyGoal.calories + meal.calories,
            protein = dailyGoal.protein + meal.protein,
            carb = dailyGoal.carb + meal.carb,
            fat = dailyGoal.fat + meal.fat,
        )
    }
}
