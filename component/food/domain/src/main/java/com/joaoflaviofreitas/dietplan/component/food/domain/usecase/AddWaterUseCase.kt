package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface AddWaterUseCase {
    suspend fun execute(userEmail: String): Boolean
}
