package com.joaoflaviofreitas.dietplan.feature.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal // ktlint-disable no-wildcard-imports
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.*
import com.joaoflaviofreitas.dietplan.feature.common.utils.formatPropertiesWithTwoDecimalPlaces
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

    private val _goalAchieved = MutableLiveData(DailyGoal())
    val goalAchieved get() = _goalAchieved

    private val _dailyDiet = MutableLiveData<DailyGoal>()
    val dailyDiet get() = _dailyDiet
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
    override fun incrementNutrientsToDailyDiet(meal: Meal, dailyGoal: DailyGoal) {
        val daily = getDailyGoalUseCase.execute(meal, dailyGoal)
        _goalAchieved.postValue(daily)
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

    override suspend fun incWater(): Boolean {
        return addWaterUseCase.execute()
    }

    override fun submitDailyDiet(nutrients: DailyGoal) {
        viewModelScope.launch {
            saveDailyGoalInDatabaseUseCase.execute(nutrients)
        }
    }

    override suspend fun getDailyDiet(): DailyGoal {
        return getDailyGoalInDatabaseUseCase.execute()
    }

    override fun submitAchievedGoal(achievedGoal: AchievedGoal) {
        viewModelScope.launch {
            saveAchievedGoalInDatabaseUseCase.execute(achievedGoal)
        }
    }

    override suspend fun getAchievedGoal(): AchievedGoal {
        return getAchievedGoalInDatabaseUseCase.execute()
    }

    override fun updateAchievedGoal(achievedGoal: AchievedGoal) {
        viewModelScope.launch {
            updateAchievedGoalInDatabaseUseCase.execute(achievedGoal)
        }
    }
}
