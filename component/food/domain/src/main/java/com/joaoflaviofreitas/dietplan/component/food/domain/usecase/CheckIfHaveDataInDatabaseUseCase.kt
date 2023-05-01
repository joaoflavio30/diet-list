package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface CheckIfHaveDataInDatabaseUseCase {

    suspend fun execute(foodName: String): Boolean
}
