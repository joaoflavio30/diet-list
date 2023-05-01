package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class GetDailyGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    GetDailyGoalInDatabaseUseCase {
    override suspend fun execute(): DailyGoal {
        return repository.getDailyGoal()
    }
}
