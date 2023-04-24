package com.example.dietplan.searchfood.usecases

interface CheckIfHaveDataInDatabaseUseCase {

    suspend fun execute(foodName: String): Boolean
}
