package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class UpdateDailyGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    UpdateDailyGoalInDatabaseUseCase {
    override suspend fun execute(dailyGoal: DailyGoal): Boolean {
        return repository.updateDailyGoal(dailyGoal)
    }
}
