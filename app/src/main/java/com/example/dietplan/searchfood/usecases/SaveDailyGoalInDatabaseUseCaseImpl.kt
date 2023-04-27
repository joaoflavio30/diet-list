package com.example.dietplan.searchfood.usecases

import com.example.dietplan.domain.model.DataState
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.repositories.MealRepository

class SaveDailyGoalInDatabaseUseCaseImpl(private val repository: MealRepository): SaveDailyGoalInDatabaseUseCase {
    override suspend fun execute(dailyGoal: DailyGoal): com.example.dietplan.domain.model.DataState<Boolean> {
        return repository.saveDailyGoal(dailyGoal)
    }
}
