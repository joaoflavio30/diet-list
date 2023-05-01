package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class CheckIfHaveDataInDatabaseUseCaseImpl(private val repository: MealRepository):
    CheckIfHaveDataInDatabaseUseCase {
    override suspend fun execute(foodName: String): Boolean {
        return repository.findByName(foodName) != null
    }
}
