package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class UpdateAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    UpdateAchievedGoalInDatabaseUseCase {
    override suspend fun execute(achievedGoal: AchievedGoal): Boolean {
        val oldAchievedGoal = repository.getAchievedGoal(achievedGoal.userEmail!!)
        val newAchievedGoal = oldAchievedGoal.copy(
            calories = oldAchievedGoal.calories.plus(achievedGoal.calories),
            protein = oldAchievedGoal.protein.plus(achievedGoal.protein),
            carb = oldAchievedGoal.carb.plus(achievedGoal.carb),
            fat = oldAchievedGoal.fat.plus(achievedGoal.fat),
        )
        return repository.updateAchievedGoal(newAchievedGoal)
    }
}
