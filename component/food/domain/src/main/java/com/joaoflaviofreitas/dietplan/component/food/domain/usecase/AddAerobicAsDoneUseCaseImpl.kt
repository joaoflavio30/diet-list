package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import kotlinx.coroutines.flow.last

class AddAerobicAsDoneUseCaseImpl(private val repository: MealRepository): AddAerobicAsDoneUseCase {
    override suspend fun execute(userEmail: String): Boolean {
        return try {
            val achievedGoal = repository.getAchievedGoal(userEmail)
            achievedGoal.apply { aerobic = true }
            repository.updateAchievedGoal(achievedGoal)
            true
        } catch (e: Exception) {
            false
        }
    }
}