package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface AddWaterUseCase {
    suspend fun execute(): Boolean
}
