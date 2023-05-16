package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal

interface GetAchievedGoalInDatabaseUseCase {

    suspend fun execute(userEmail: String): AchievedGoal
}
