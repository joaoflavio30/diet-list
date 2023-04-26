package com.example.dietplan.searchfood.usecases

import com.example.dietplan.DataState
import com.example.dietplan.data.model.AchievedGoal

interface SaveAchievedGoalInDatabaseUseCase {
    suspend fun execute(achievedGoal: AchievedGoal): DataState<Boolean>
}
