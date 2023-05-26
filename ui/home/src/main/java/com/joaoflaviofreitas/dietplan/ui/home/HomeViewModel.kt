package com.joaoflaviofreitas.dietplan.ui.home

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
class HomeViewModel @Inject constructor(
    private val getAchievedGoalInDatabaseUseCase: GetAchievedGoalInDatabaseUseCase,
    private val getDailyGoalInDatabaseUseCase: GetDailyGoalInDatabaseUseCase,
    private val addWaterUseCase: AddWaterUseCase,
    private val resetAchievedGoalUseCase: ResetAchievedGoalUseCase,
    private val addAerobicAsDoneUseCase: AddAerobicAsDoneUseCase,
    private val resetDailyGoalUseCase: ResetDailyGoalUseCase,
): ViewModel(), HomeContract.HomeViewModel {

    val loadAchievedGoal: MutableLiveData<AchievedGoal> by lazy {
        MutableLiveData<AchievedGoal>()
    }

    val loadDailyGoal: MutableLiveData<DailyGoal> by lazy {
        MutableLiveData<DailyGoal>()
    }

    override suspend fun getAchievedGoal(userEmail: String) {
        viewModelScope.launch {
            loadAchievedGoal.postValue(getAchievedGoalInDatabaseUseCase.execute(userEmail))
        }
    }
    override suspend fun getDailyDiet(userEmail: String) {
        viewModelScope.launch {
            loadDailyGoal.postValue(getDailyGoalInDatabaseUseCase.execute(userEmail))
        }
    }

    override suspend fun incWater(userEmail: String, achievedGoal: AchievedGoal): Boolean {
        return addWaterUseCase.execute(userEmail, achievedGoal)
    }

    override suspend fun resetAchievedGoal(userEmail: String, currentDate: String, achievedGoal: AchievedGoal) {
        resetAchievedGoalUseCase.execute(userEmail, currentDate, achievedGoal)
    }

    override fun resetDailyGoal(userEmail: String) {
        viewModelScope.launch {
            resetDailyGoalUseCase.execute(userEmail)
        }
    }

    override suspend fun addAerobicAsDone(userEmail: String): Boolean {
        return addAerobicAsDoneUseCase.execute(userEmail)
    }
}
