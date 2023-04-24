package com.example.dietplan.searchfood.usecases

import com.example.dietplan.data.local.Meal
import com.example.dietplan.repositories.MealRepository

class SaveMealInDatabaseUseCaseImpl(private val repository: MealRepository):SaveMealInDatabaseUseCase {
    override suspend fun execute(meal: Meal) {
        repository.saveMealInDatabase(meal)
    }
}
