package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal

interface ResetAchievedGoalUseCase {

    suspend fun execute(userEmail: String, currentDate: String, achievedGoal: AchievedGoal)
}
