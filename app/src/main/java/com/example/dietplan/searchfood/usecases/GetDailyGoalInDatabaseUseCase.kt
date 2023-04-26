package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.model.DailyGoal

interface GetDailyGoalInDatabaseUseCase {

    suspend fun execute(): DailyGoal
}
