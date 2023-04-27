package com.example.dietplan.searchfood.usecases

import com.example.dietplan.domain.model.DataState

interface AddWaterUseCase {
    suspend fun execute(): com.example.dietplan.domain.model.DataState<Boolean>
}