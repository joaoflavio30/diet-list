package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class ResetAchievedGoalUseCaseImpl(private val repository: MealRepository): ResetAchievedGoalUseCase {
    override suspend fun execute(userEmail: String, currentDate: String, achievedGoal: AchievedGoal) {
        if (achievedGoal.date != currentDate) {
            repository.updateAchievedGoal(AchievedGoal(userEmail, id = achievedGoal.id, date = currentDate))
        }
    }
}
