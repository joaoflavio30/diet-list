package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class SaveAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    SaveAchievedGoalInDatabaseUseCase {
    override suspend fun execute(achievedGoal: AchievedGoal): Boolean {
        return repository.saveAchievedGoal(achievedGoal)
    }
}
