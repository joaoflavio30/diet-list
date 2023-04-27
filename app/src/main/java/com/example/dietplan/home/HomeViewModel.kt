package com.example.dietplan.home

import androidx.lifecycle.ViewModel
import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.domain.model.DataState
import com.example.dietplan.searchfood.usecases.AddWaterUseCase
import com.example.dietplan.searchfood.usecases.GetAchievedGoalInDatabaseUseCase
import com.example.dietplan.searchfood.usecases.GetDailyGoalInDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAchievedGoalInDatabaseUseCase: GetAchievedGoalInDatabaseUseCase,
    private val getDailyGoalInDatabaseUseCase: GetDailyGoalInDatabaseUseCase,
    private val addWaterUseCase: AddWaterUseCase,
): ViewModel(), HomeContract.HomeViewModel {
    override suspend fun getAchievedGoal(): AchievedGoal {
        return getAchievedGoalInDatabaseUseCase.execute()
    }

    override suspend fun getDailyDiet(): DailyGoal {
        return getDailyGoalInDatabaseUseCase.execute()
    }

    override suspend fun incWater(): DataState<Boolean> {
        return addWaterUseCase.execute()
    }
}
