package com.example.dietplan.searchfood.usecases

import android.util.Log
import com.example.dietplan.DataState
import com.example.dietplan.repositories.MealRepository

class AddWaterUseCaseImpl(private val repository: MealRepository): AddWaterUseCase {
    override suspend fun execute(): DataState<Boolean> {
        return try {
            val achievedGoal = repository.getAchievedGoal()
            achievedGoal.apply { water+=1 }
            Log.d("testeAchievedGoal","$achievedGoal")
            repository.updateAchievedGoal(achievedGoal)
            DataState.Success(true)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}
