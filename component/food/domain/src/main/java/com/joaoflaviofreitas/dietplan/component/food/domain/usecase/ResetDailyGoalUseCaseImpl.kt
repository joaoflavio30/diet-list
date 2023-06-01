package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class ResetDailyGoalUseCaseImpl(private val repository: MealRepository): ResetDailyGoalUseCase {
    override suspend fun execute(userEmail: String) {
        repository.resetDailyGoal(userEmail)
    }
}
