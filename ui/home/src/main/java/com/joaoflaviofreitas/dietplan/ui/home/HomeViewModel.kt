package com.joaoflaviofreitas.dietplan.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
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

    override suspend fun getAchievedGoal() {
        viewModelScope.launch {
            loadAchievedGoal.postValue(getAchievedGoalInDatabaseUseCase.execute(auth.currentUser!!.email!!))
        }
    }
    override suspend fun getDailyDiet() {
        viewModelScope.launch {
            loadDailyGoal.postValue(getDailyGoalInDatabaseUseCase.execute(auth.currentUser!!.email!!))
        }
    }

    override suspend fun incWater(achievedGoal: AchievedGoal): Boolean {
        return addWaterUseCase.execute(auth.currentUser!!.email!!, achievedGoal)
    }

    override suspend fun resetAchievedGoal(currentDate: String, achievedGoal: AchievedGoal) {
        resetAchievedGoalUseCase.execute(auth.currentUser!!.email!!, currentDate, achievedGoal)
    }

    override fun resetDailyGoal() {
        viewModelScope.launch {
            resetDailyGoalUseCase.execute(auth.currentUser!!.email!!)
        }
    }

    override suspend fun addAerobicAsDone(): Boolean {
        return addAerobicAsDoneUseCase.execute(auth.currentUser!!.email!!)
    }
}
