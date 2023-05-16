package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class CheckIfDailyGoalExistsByEmailUseCaseImpl(private val repository: MealRepository): CheckIfDailyGoalExistsByEmailUseCase {
    override fun execute(userEmail: String): Boolean {
        return repository.dailyGoalExistsByEmail(userEmail)
    }
}
