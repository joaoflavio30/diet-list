package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.model.AchievedGoal

interface GetAchievedGoalInDatabaseUseCase {

    suspend fun execute(): AchievedGoal
}
