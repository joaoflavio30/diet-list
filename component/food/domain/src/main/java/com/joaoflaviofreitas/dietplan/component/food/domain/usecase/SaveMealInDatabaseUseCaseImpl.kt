package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository

class SaveMealInDatabaseUseCaseImpl(private val repository: MealRepository):
    SaveMealInDatabaseUseCase {
    override suspend fun execute(meal: Meal) {
        repository.saveMealInDatabase(meal)
    }
}
