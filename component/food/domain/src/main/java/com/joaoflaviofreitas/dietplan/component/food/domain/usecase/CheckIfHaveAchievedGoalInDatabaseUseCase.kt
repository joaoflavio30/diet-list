package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface CheckIfHaveAchievedGoalInDatabaseUseCase {
    suspend fun execute(userEmail: String): Boolean
}