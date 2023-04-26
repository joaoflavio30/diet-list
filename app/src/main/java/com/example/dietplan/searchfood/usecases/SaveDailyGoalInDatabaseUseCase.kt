package com.example.dietplan.searchfood.usecases

import com.example.dietplan.DataState
import com.example.dietplan.data.model.DailyGoal

interface SaveDailyGoalInDatabaseUseCase {
    suspend fun execute(dailyGoal: DailyGoal): DataState<Boolean>
}