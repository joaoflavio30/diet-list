package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal

interface SaveDailyGoalInDatabaseUseCase {
    suspend fun execute(dailyGoal: DailyGoal): Boolean
}