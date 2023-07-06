package com.joaoflaviofreitas.dietplan.component.food.domain.usecase

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import java.text.DecimalFormat

internal class UpdateAchievedGoalInDatabaseUseCaseImpl(private val repository: MealRepository):
    UpdateAchievedGoalInDatabaseUseCase {
    override suspend fun execute(meal: Meal, achievedGoal: AchievedGoal): Boolean {
        val newAchievedGoal = achievedGoal.copy(
            calories = format(achievedGoal.calories.plus(meal.calories)),
            protein = format(achievedGoal.protein.plus(meal.protein)),
            carb = format(achievedGoal.carb.plus(meal.carb)),
            fat = format(achievedGoal.fat.plus(meal.fat)),
        )
        return repository.updateAchievedGoal(newAchievedGoal)
    }
    private fun format(numb: Double) = DecimalFormat("#.0").format(numb).replace(",", ".").toDouble()
}
