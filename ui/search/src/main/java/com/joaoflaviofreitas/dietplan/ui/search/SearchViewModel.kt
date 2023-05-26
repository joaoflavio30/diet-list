package com.joaoflaviofreitas.dietplan.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal // ktlint-disable no-wildcard-imports
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.*
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.formatPropertiesWithTwoDecimalPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMealByApiUseCase: GetMealByApiUseCase,
    private val getMealByDatabaseUseCase: GetMealByDatabaseUseCase,
    private val saveMealInDatabaseUseCase: SaveMealInDatabaseUseCase,
    private val getDailyGoalUseCase: GetDailyGoalUseCase,
    private val checkIfHaveDataInDatabaseUseCase: CheckIfHaveDataInDatabaseUseCase,
    private val getDailyGoalInDatabaseUseCase: GetDailyGoalInDatabaseUseCase,
    private val saveDailyGoalInDatabaseUseCase: SaveDailyGoalInDatabaseUseCase,
    private val saveAchievedGoalInDatabaseUseCase: SaveAchievedGoalInDatabaseUseCase,
    private val getAchievedGoalInDatabaseUseCase: GetAchievedGoalInDatabaseUseCase,
    private val updateAchievedGoalInDatabaseUseCase: UpdateAchievedGoalInDatabaseUseCase,
    private val updateDailyGoalInDatabaseUseCase: UpdateDailyGoalInDatabaseUseCase,
    private val addWaterUseCase: AddWaterUseCase,
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

    override suspend fun isPossibleToFetchDataOffline(ingredient: RequestFood): Boolean {
        return checkIfHaveDataInDatabaseUseCase.execute(ingredient.foodName)
    }

    override fun getMeal(ingredient: RequestFood) {
        viewModelScope.launch {
            if (isPossibleToFetchDataOffline(ingredient)) {
                _searchMeal.postValue(getMealByDatabaseUseCase.execute(ingredient))
            } else {
                val currentMeal = getMealByApiUseCase.execute(ingredient) ?: Meal()
                formatPropertiesWithTwoDecimalPlaces(currentMeal)
                _searchMeal.postValue(currentMeal)
            }
        }
    }

    override fun resetDailyDietWhenMidnight() {
        viewModelScope.launch {
        }
    }

    override fun saveRemoteDataInDatabase(meal: Meal) {
        viewModelScope.launch {
            saveMealInDatabaseUseCase.execute(meal)
        }
    }

    override fun submitDailyDiet(nutrients: DailyGoal) {
        viewModelScope.launch {
            saveDailyGoalInDatabaseUseCase.execute(nutrients)
        }
    }

    override suspend fun getDailyDiet(userEmail: String) {
        viewModelScope.launch {
            loadDailyGoal.postValue(getDailyGoalInDatabaseUseCase.execute(userEmail))
        }
    }

    override fun submitAchievedGoal(achievedGoal: AchievedGoal) {
        viewModelScope.launch {
            formatPropertiesWithTwoDecimalPlaces(achievedGoal)
            saveAchievedGoalInDatabaseUseCase.execute(achievedGoal)
        }
    }

    override suspend fun getAchievedGoal(userEmail: String) {
        viewModelScope.launch {
            loadAchievedGoal.postValue(getAchievedGoalInDatabaseUseCase.execute(userEmail))
        }
    }

    override fun updateAchievedGoal(meal: Meal, achievedGoal: AchievedGoal) {
        viewModelScope.launch {
            updateAchievedGoalInDatabaseUseCase.execute(meal, achievedGoal)
        }
    }
}
