package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood

interface GetMealByDatabaseUseCase {

    suspend fun execute(ingredient: RequestFood): Meal?
}
