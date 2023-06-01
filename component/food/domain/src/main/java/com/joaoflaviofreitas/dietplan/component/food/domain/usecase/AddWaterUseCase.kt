package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal

interface AddWaterUseCase {
    suspend fun execute(userEmail: String, achievedGoal: AchievedGoal): Boolean
}
