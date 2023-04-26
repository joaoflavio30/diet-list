package com.example.dietplan.searchfood.usecases

import com.example.dietplan.DataState
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.repositories.MealRepository

class SaveDailyGoalInDatabaseUseCaseImpl(private val repository: MealRepository): SaveDailyGoalInDatabaseUseCase {
    override suspend fun execute(dailyGoal: DailyGoal): DataState<Boolean> {
        return repository.saveDailyGoal(dailyGoal)
    }
}
