package com.joaoflaviofreitas.dietplan.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal // ktlint-disable no-wildcard-imports
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.*
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.formatPropertiesWithTwoDecimalPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getMealByApiUseCase: GetMealByApiUseCase,
    private val getMealByDatabaseUseCase: GetMealByDatabaseUseCase,
    private val checkIfHaveDataInDatabaseUseCase: CheckIfHaveDataInDatabaseUseCase,
    private val getDailyGoalInDatabaseUseCase: GetDailyGoalInDatabaseUseCase,
    private val saveAchievedGoalInDatabaseUseCase: SaveAchievedGoalInDatabaseUseCase,
    private val getAchievedGoalInDatabaseUseCase: GetAchievedGoalInDatabaseUseCase,
    private val updateAchievedGoalInDatabaseUseCase: UpdateAchievedGoalInDatabaseUseCase,
) : ViewModel(), SearchContract.SearchViewModel {

    private val _searchMeal = MutableLiveData<Meal>()
    val searchMeal get() = _searchMeal

    private val _goalAchieved = MutableLiveData(AchievedGoal())
    val goalAchieved get() = _goalAchieved

    private val _dailyDiet = MutableLiveData<DailyGoal>()
    val dailyDiet get() = _dailyDiet

    val loadAchievedGoal: MutableLiveData<AchievedGoal> by lazy {
        MutableLiveData<AchievedGoal>()
    }

    val loadDailyGoal: MutableLiveData<DailyGoal> by lazy {
        MutableLiveData<DailyGoal>()
    }
    private var job: Job = Job()

    override suspend fun isPossibleToFetchDataOffline(ingredient: RequestFood): Boolean {
        return checkIfHaveDataInDatabaseUseCase.execute(ingredient.foodName)
    }

    override fun getMeal(ingredient: RequestFood) {
        job.cancel()
        job = viewModelScope.launch {
            delay(600)
            if (isPossibleToFetchDataOffline(ingredient)) {
                _searchMeal.postValue(getMealByDatabaseUseCase.execute(ingredient))
            } else {
                val currentMeal = getMealByApiUseCase.execute(ingredient) ?: Meal()
                formatPropertiesWithTwoDecimalPlaces(currentMeal)
                _searchMeal.postValue(currentMeal)
            }
        }
    }

    override suspend fun getDailyDiet() {
        viewModelScope.launch {
            loadDailyGoal.postValue(getDailyGoalInDatabaseUseCase.execute(auth.currentUser!!.email!!))
        }
    }

    override fun submitAchievedGoal(achievedGoal: AchievedGoal) {
        viewModelScope.launch {
            formatPropertiesWithTwoDecimalPlaces(achievedGoal)
            saveAchievedGoalInDatabaseUseCase.execute(achievedGoal)
        }
    }

    override suspend fun getAchievedGoal() {
        viewModelScope.launch {
            loadAchievedGoal.postValue(getAchievedGoalInDatabaseUseCase.execute(auth.currentUser!!.email!!))
        }
    }

    override fun updateAchievedGoal(meal: Meal, achievedGoal: AchievedGoal) {
        viewModelScope.launch {
            updateAchievedGoalInDatabaseUseCase.execute(meal, achievedGoal)
        }
    }
}
