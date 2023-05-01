package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import javax.inject.Inject

class GetMealByDatabaseUseCaseImpl @Inject constructor(private val repository: MealRepository):
    GetMealByDatabaseUseCase {
    override suspend fun execute(ingredient: RequestFood): Meal? {
        val body = repository.getNutrientsByDatabase(ingredient)
        if (body != null) {
            return Meal(foodName = ingredient.foodName, measure = ingredient.measure, protein = body.protein, carb = body.carb, fat = body.fat, quantity = body.quantity, calories = body.calories)
        } else {
            return null
        }
    }
}
