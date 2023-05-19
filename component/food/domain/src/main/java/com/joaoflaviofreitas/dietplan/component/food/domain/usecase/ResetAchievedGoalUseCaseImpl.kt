package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

internal class ResetAchievedGoalUseCaseImpl(private val repository: MealRepository): ResetAchievedGoalUseCase {
    override suspend fun execute(userEmail: String, currentDate: String) {
        if (repository.dailyGoalExistsByEmail(userEmail) && repository.getAchievedGoal(userEmail).date != currentDate) {
            repository.updateAchievedGoal(AchievedGoal(userEmail, id = repository.getAchievedGoal(userEmail).id, date = currentDate))
        }
    }
}
