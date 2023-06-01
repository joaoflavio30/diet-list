package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow

internal class GetAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    GetAchievedGoalInDatabaseUseCase {
    override suspend fun execute(userEmail: String): AchievedGoal {
        return repository.getAchievedGoal(userEmail)
    }
}
