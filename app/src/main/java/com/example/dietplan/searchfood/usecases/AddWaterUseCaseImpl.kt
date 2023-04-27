package com.example.dietplan.searchfood.usecases

import android.util.Log
import com.example.dietplan.domain.model.DataState
import com.example.dietplan.repositories.MealRepository

class AddWaterUseCaseImpl(private val repository: MealRepository): AddWaterUseCase {
    override suspend fun execute(): com.example.dietplan.domain.model.DataState<Boolean> {
        return try {
            val achievedGoal = repository.getAchievedGoal()
            achievedGoal.apply { water+=1 }
            Log.d("testeAchievedGoal","$achievedGoal")
            repository.updateAchievedGoal(achievedGoal)
            com.example.dietplan.domain.model.DataState.Success(true)
        } catch (e: Exception) {
            com.example.dietplan.domain.model.DataState.Error(e)
        }
    }
}
