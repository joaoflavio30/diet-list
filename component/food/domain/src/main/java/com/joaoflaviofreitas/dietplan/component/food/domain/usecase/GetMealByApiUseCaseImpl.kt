package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import javax.inject.Inject

class GetMealByApiUseCaseImpl @Inject constructor(private val repository: MealRepository):
    GetMealByApiUseCase {
    override suspend fun execute(ingredient: RequestFood): Meal? {
        val body = repository.getNutrientsByApi(ingredient).body()
        if (body != null && body.calories != 0.0) {
            return Meal(
                foodName = ingredient.foodName,
                measure = ingredient.measure,
                protein = body.totalNutrients["PROCNT"]!!.quantity,
                carb =
                body.totalNutrients["CHOCDF"]!!.quantity,
                fat = body.totalNutrients["FAT"]!!.quantity,
                quantity = ingredient.quantity,
                calories = body.calories,
            )
        } else {
            return null
        }
    }
}
