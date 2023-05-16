package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class SaveDailyGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    SaveDailyGoalInDatabaseUseCase {
    override suspend fun execute(dailyGoal: DailyGoal): Boolean {
        return repository.saveDailyGoal(dailyGoal)
    }
}
