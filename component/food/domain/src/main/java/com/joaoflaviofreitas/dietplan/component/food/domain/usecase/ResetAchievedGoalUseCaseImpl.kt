package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class ResetAchievedGoalUseCaseImpl(private val repository: MealRepository): ResetAchievedGoalUseCase {
    override suspend fun execute(userEmail: String) {
        repository.updateAchievedGoal(AchievedGoal(userEmail, id = repository.getAchievedGoal(userEmail).id))
    }
}
