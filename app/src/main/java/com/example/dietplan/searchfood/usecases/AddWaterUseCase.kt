package com.example.dietplan.searchfood.usecases

import com.example.dietplan.DataState

interface AddWaterUseCase {
    suspend fun execute(): DataState<Boolean>
}