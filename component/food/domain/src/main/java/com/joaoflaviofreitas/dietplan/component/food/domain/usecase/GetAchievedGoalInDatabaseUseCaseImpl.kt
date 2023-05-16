package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class GetAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    GetAchievedGoalInDatabaseUseCase {
    override suspend fun execute(userEmail: String): AchievedGoal {
        return repository.getAchievedGoal(userEmail)
    }
}
