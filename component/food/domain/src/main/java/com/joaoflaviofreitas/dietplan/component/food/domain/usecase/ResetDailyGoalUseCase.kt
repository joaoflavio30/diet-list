package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface ResetDailyGoalUseCase {
    suspend fun execute(userEmail: String)
}
