package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal

interface UpdateDailyGoalInDatabaseUseCase {

    suspend fun execute(dailyGoal: DailyGoal): Boolean
}
