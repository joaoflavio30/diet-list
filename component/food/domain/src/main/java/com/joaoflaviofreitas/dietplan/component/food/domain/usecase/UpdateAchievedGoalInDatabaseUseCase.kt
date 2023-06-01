package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

interface UpdateAchievedGoalInDatabaseUseCase {
    suspend fun execute(meal: Meal, achievedGoal: AchievedGoal): Boolean
}
