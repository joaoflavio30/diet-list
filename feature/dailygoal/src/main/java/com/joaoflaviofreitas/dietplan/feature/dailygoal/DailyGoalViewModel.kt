package com.joaoflaviofreitas.dietplan.feature.dailygoal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.CheckIfDailyGoalExistsByEmailUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.SaveAchievedGoalInDatabaseUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.SaveDailyGoalInDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyGoalViewModel @Inject constructor(
    private val saveAchievedGoalInDatabaseUseCase: SaveAchievedGoalInDatabaseUseCase,
    private val saveDailyGoalInDatabaseUseCase: SaveDailyGoalInDatabaseUseCase,
    private val checkIfDailyGoalExistsByEmailUseCase: CheckIfDailyGoalExistsByEmailUseCase,
): ViewModel(), DailyGoalContract.DailyGoalViewModel {

    private val _userMakesDailyGoal = MutableLiveData<Boolean>()
    val userMakesDailyGoal get() = _userMakesDailyGoal

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

    override fun checkIfUserMakesDailyGoal(userEmail: String) {
        viewModelScope.launch {
            when (val result = checkIfDailyGoalExistsByEmailUseCase.execute(userEmail)) {
                true -> _userMakesDailyGoal.postValue(true)
                false -> _userMakesDailyGoal.postValue(false)
            }
        }
    }
}
