package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class AddWaterUseCaseImpl(private val repository: MealRepository): AddWaterUseCase {
    override suspend fun execute(): Boolean {
        return try {
            val achievedGoal = repository.getAchievedGoal()
            achievedGoal.apply { water += 1 }
            repository.updateAchievedGoal(achievedGoal)
            true
        } catch (e: Exception) {
            false
        }
    }
}
