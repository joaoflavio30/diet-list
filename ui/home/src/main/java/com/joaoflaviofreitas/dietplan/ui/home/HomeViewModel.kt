package com.joaoflaviofreitas.dietplan.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAchievedGoalInDatabaseUseCase: GetAchievedGoalInDatabaseUseCase,
    private val getDailyGoalInDatabaseUseCase: GetDailyGoalInDatabaseUseCase,
    private val addWaterUseCase: AddWaterUseCase,
    private val resetAchievedGoalUseCase: ResetAchievedGoalUseCase,
): ViewModel(), HomeContract.HomeViewModel {
    override suspend fun getAchievedGoal(userEmail: String): AchievedGoal {
        val achievedGoal = getAchievedGoalInDatabaseUseCase.execute(userEmail)
        Log.d("teste", "${getAchievedGoalInDatabaseUseCase.execute(userEmail)}")
//        formatPropertiesWithTwoDecimalPlaces(achievedGoal)
        return achievedGoal
    }

    override suspend fun getDailyDiet(userEmail: String): DailyGoal {
        Log.d("teste", "${getDailyGoalInDatabaseUseCase.execute(userEmail)}")
        return getDailyGoalInDatabaseUseCase.execute(userEmail)!!
    }

    override suspend fun incWater(userEmail: String): Boolean {
        return addWaterUseCase.execute(userEmail)
    }

    override fun resetAchievedGoal(userEmail: String, currentDate: String) {
        viewModelScope.launch {
            resetAchievedGoalUseCase.execute(userEmail, currentDate)
        }
    }
}
