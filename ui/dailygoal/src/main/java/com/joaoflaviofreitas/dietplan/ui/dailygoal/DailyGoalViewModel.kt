package com.joaoflaviofreitas.dietplan.ui.dailygoal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyGoalViewModel @Inject constructor(
    private val saveAchievedGoalInDatabaseUseCase: SaveAchievedGoalInDatabaseUseCase,
    private val saveDailyGoalInDatabaseUseCase: SaveDailyGoalInDatabaseUseCase,
    private val checkIfHaveAchievedGoalInDatabaseUseCase: CheckIfHaveAchievedGoalInDatabaseUseCase,
): ViewModel(), DailyGoalContract.DailyGoalViewModel {

    private val _userMakesAchievedGoal = MutableLiveData<Boolean>()
    val userMakesAchievedGoal get() = _userMakesAchievedGoal

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

    override fun checkIfExistsAchievedGoal(userEmail: String) {
        viewModelScope.launch {
            when (val result = checkIfHaveAchievedGoalInDatabaseUseCase.execute(userEmail)) {
                true -> _userMakesAchievedGoal.postValue(true)
                false -> _userMakesAchievedGoal.postValue(false)
            }
        }
    }
}
