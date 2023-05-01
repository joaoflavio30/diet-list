package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal

interface UpdateAchievedGoalInDatabaseUseCase {
    suspend fun execute(achievedGoal: AchievedGoal): Boolean
}
