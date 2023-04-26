package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.repositories.MealRepository

class GetDailyGoalInDatabaseUseCaseImpl(private val repository: MealRepository): GetDailyGoalInDatabaseUseCase {
    override suspend fun execute(): DailyGoal {
        return repository.getDailyGoal()
    }
}
