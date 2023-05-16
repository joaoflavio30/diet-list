package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface CheckIfDailyGoalExistsByEmailUseCase {
    fun execute(userEmail: String): Boolean
}
