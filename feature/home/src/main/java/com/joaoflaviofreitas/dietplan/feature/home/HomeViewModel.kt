package com.joaoflaviofreitas.dietplan.feature.home

import androidx.lifecycle.ViewModel
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.AddWaterUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.GetAchievedGoalInDatabaseUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.GetDailyGoalInDatabaseUseCase
import com.joaoflaviofreitas.dietplan.feature.common.utils.formatPropertiesWithTwoDecimalPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAchievedGoalInDatabaseUseCase: GetAchievedGoalInDatabaseUseCase,
    private val getDailyGoalInDatabaseUseCase: GetDailyGoalInDatabaseUseCase,
    private val addWaterUseCase: AddWaterUseCase,
): ViewModel(), HomeContract.HomeViewModel {
    override suspend fun getAchievedGoal(): AchievedGoal {
        val achievedGoal = getAchievedGoalInDatabaseUseCase.execute()
        formatPropertiesWithTwoDecimalPlaces(achievedGoal)
        return achievedGoal
    }

    override suspend fun getDailyDiet(): DailyGoal {
        return getDailyGoalInDatabaseUseCase.execute()
    }

    override suspend fun incWater(): Boolean {
        return addWaterUseCase.execute()
    }
}
