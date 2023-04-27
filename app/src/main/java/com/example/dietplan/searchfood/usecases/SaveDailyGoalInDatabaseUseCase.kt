package com.example.dietplan.searchfood.usecases

import com.example.dietplan.domain.model.DataState
import com.example.dietplan.data.model.DailyGoal

interface SaveDailyGoalInDatabaseUseCase {
    suspend fun execute(dailyGoal: DailyGoal): com.example.dietplan.domain.model.DataState<Boolean>
}