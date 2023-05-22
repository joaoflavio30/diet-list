package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

interface AddAerobicAsDoneUseCase {
    suspend fun execute(userEmail: String): Boolean
}
