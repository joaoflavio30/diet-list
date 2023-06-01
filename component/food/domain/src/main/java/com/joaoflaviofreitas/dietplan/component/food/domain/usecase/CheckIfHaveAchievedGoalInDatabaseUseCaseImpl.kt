package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class CheckIfHaveAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository): CheckIfHaveAchievedGoalInDatabaseUseCase {
    override suspend fun execute(userEmail: String): Boolean {
        return repository.achievedGoalExistsByEmail(userEmail)
    }
}
