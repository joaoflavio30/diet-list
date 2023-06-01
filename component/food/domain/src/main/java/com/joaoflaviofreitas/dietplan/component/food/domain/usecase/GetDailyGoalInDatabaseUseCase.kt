package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal

interface GetDailyGoalInDatabaseUseCase {

    suspend fun execute(userEmail: String): DailyGoal?
}
