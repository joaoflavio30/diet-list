package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.repositories.MealRepository

class GetAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository): GetAchievedGoalInDatabaseUseCase {
    override suspend fun execute(): AchievedGoal {
        return repository.getAchievedGoal()
    }
}
