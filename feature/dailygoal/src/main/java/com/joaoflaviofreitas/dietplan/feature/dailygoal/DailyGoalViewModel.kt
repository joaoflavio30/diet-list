package com.joaoflaviofreitas.dietplan.feature.dailygoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.SaveAchievedGoalInDatabaseUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.SaveDailyGoalInDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyGoalViewModel @Inject constructor(
    private val saveAchievedGoalInDatabaseUseCase: SaveAchievedGoalInDatabaseUseCase,
    private val saveDailyGoalInDatabaseUseCase: SaveDailyGoalInDatabaseUseCase,
): ViewModel(), DailyGoalContract.DailyGoalViewModel {
    override fun submitDailyDiet(nutrients: DailyGoal) {
        viewModelScope.launch {
            saveDailyGoalInDatabaseUseCase.execute(nutrients)
        }
    }

    override fun submitAchievedGoal(achievedGoal: AchievedGoal) {
        viewModelScope.launch {
            saveAchievedGoalInDatabaseUseCase.execute(achievedGoal)
        }
    }
}
