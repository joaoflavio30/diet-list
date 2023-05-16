package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface ResetAchievedGoalUseCase {

    suspend fun execute(userEmail: String)
}
