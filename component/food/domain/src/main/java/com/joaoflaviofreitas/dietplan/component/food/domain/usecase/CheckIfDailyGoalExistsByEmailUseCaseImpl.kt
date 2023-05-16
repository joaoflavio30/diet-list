package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class CheckIfDailyGoalExistsByEmailUseCaseImpl(private val repository: MealRepository): CheckIfDailyGoalExistsByEmailUseCase {
    override suspend fun execute(userEmail: String): Boolean {
        return repository.dailyGoalExistsByEmail(userEmail)
    }
}
