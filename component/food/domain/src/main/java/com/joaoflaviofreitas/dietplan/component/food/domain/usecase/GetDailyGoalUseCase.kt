package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

interface GetDailyGoalUseCase {

    fun execute(meal: Meal, dailyGoal: DailyGoal): DailyGoal
}
