package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

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
