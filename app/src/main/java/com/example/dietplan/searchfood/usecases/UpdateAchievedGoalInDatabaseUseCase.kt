package com.example.dietplan.searchfood.usecases

import com.example.dietplan.domain.model.DataState
import com.example.dietplan.data.model.AchievedGoal

interface UpdateAchievedGoalInDatabaseUseCase {
    suspend fun execute(achievedGoal: AchievedGoal): com.example.dietplan.domain.model.DataState<Boolean>
}
