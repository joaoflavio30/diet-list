package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class AddWaterUseCaseImpl(private val repository: MealRepository): AddWaterUseCase {
    override suspend fun execute(userEmail: String, achievedGoal: AchievedGoal): Boolean {
        return try {
            achievedGoal.apply { water += 1 }
            repository.updateAchievedGoal(achievedGoal)
            true
        } catch (e: Exception) {
            false
        }
    }
}
