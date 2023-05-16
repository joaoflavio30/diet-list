package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface CheckIfDailyGoalExistsByEmailUseCase {
    suspend fun execute(userEmail: String): Boolean
}
