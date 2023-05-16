package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class GetDailyGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    GetDailyGoalInDatabaseUseCase {
    override suspend fun execute(userEmail: String): DailyGoal? {
        return repository.getDailyGoal(userEmail)
    }
}
