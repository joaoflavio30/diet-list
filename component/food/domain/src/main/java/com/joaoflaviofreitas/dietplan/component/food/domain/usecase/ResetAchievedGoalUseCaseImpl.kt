package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class ResetAchievedGoalUseCaseImpl(private val repository: MealRepository): ResetAchievedGoalUseCase {
    override suspend fun execute() {
        repository.updateAchievedGoal(AchievedGoal())
    }
}
