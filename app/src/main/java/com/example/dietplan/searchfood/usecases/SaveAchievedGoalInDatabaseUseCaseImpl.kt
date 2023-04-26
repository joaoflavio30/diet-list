package com.example.dietplan.searchfood.usecases

import com.example.dietplan.DataState
import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.repositories.MealRepository

class SaveAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository): SaveAchievedGoalInDatabaseUseCase {
    override suspend fun execute(achievedGoal: AchievedGoal): DataState<Boolean> {
        return repository.saveAchievedGoal(achievedGoal)
    }
}
