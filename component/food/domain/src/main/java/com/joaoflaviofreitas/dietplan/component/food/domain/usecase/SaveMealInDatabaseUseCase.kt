package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

interface SaveMealInDatabaseUseCase {

    suspend fun execute(meal: Meal)
}
