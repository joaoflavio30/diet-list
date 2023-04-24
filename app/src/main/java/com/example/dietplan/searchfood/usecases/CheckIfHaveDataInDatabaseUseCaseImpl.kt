package com.example.dietplan.searchfood.usecases

import com.example.dietplan.repositories.MealRepository

class CheckIfHaveDataInDatabaseUseCaseImpl(private val repository: MealRepository): CheckIfHaveDataInDatabaseUseCase {
    override suspend fun execute(foodName: String): Boolean {
        return repository.findByName(foodName) != null
    }
}
