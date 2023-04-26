package com.example.dietplan.searchfood.usecases

import android.util.Log
import com.example.dietplan.DataState
import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.extensions.formatToTwoHouses
import com.example.dietplan.repositories.MealRepository

class UpdateAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository): UpdateAchievedGoalInDatabaseUseCase {
    override suspend fun execute(achievedGoal: AchievedGoal): DataState<Boolean> {
        val oldAchievedGoal = repository.getAchievedGoal()
        val newAchievedGoal = oldAchievedGoal.copy(
            calories = oldAchievedGoal.calories.plus(achievedGoal.calories).formatToTwoHouses(),
            protein = oldAchievedGoal.protein.plus(achievedGoal.protein).formatToTwoHouses(),
            carb = oldAchievedGoal.carb.plus(achievedGoal.carb).formatToTwoHouses(),
            fat = oldAchievedGoal.fat.plus(achievedGoal.fat).formatToTwoHouses(),
        )
        Log.d("teste A", "$newAchievedGoal")
        return repository.updateAchievedGoal(newAchievedGoal)
    }
}
