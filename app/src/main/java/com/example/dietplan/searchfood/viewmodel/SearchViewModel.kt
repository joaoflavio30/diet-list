package com.example.dietplan.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.data.model.ProductResponse
import com.example.dietplan.data.model.TotalNutrients
import com.example.dietplan.extensions.formatToTwoHouses
import com.example.dietplan.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _nutrients = MutableLiveData<TotalNutrients>()
    val nutrients get() = _nutrients

    private val _response = MutableLiveData<ProductResponse>()
    val response get() = _response

    private val _meal = MutableLiveData<Meal>()
    val meal get() = _meal

    private val _dailyGoal = MutableLiveData<DailyGoal>().apply {
        value =
            DailyGoal()
    }
    val dailyGoal get() = _dailyGoal

    private val _currentDaily = MutableLiveData<DailyGoal>().apply {
        value =
            DailyGoal()
    }
    val currentDaily get() = _currentDaily

    private val _stepCount = MutableLiveData<Int>()
    val stepCount get() = _stepCount

    private val _uriProfileImage = MutableLiveData<String>()
    val uriProfileImage get() = _uriProfileImage

    private val _breakfast = MutableLiveData<MutableList<Meal>>(mutableListOf())
    val breakfast get() = _breakfast

    private val _lunch = MutableLiveData<MutableList<Meal>>().apply { value = mutableListOf() }
    val lunch get() = _lunch

    private val _snack = MutableLiveData<MutableList<Meal>>().apply { value = mutableListOf() }
    val snack get() = _snack

    private val _dinner = MutableLiveData<MutableList<Meal>>().apply { value = mutableListOf() }
    val dinner get() = _dinner

    fun bindUriProfileImg(uri: String) {
        _uriProfileImage.postValue(uri)
    }

    fun bindDailyGoal(dailyGoal: DailyGoal) {
        _dailyGoal.postValue(dailyGoal)
    }

    fun incWater() {
        if (_currentDaily.value != null) {
            _currentDaily.postValue(
                currentDaily.value!!.copy(
                    water = currentDaily.value!!.water.plus(
                        1,
                    ),
                ),
            )
        }
    }

    fun bindCurrentDaily(meal: DailyGoal) {
        val current = currentDaily.value
        if (current != null) {
            current.calories = current.calories.plus(meal.calories).formatToTwoHouses()
            current.protein = current.protein.plus(meal.protein).formatToTwoHouses()
            current.carb = current.carb.plus(meal.carb).formatToTwoHouses()
            current.fat = current.fat.plus(meal.fat).formatToTwoHouses()
            _currentDaily.postValue(current)
        } else {
            _currentDaily.postValue(meal)
        }
    }

    suspend fun getFoods(ingr: String, foodName: String) {
        withContext(Dispatchers.IO) {
            val response = repository.getNutrients(ingr)

            try {
                if (response.isSuccessful) {
                    Log.d("Tag", "${response.body()}")

                    val parsed = response.body()?.ingredients?.get(0)?.parsed?.get(0)
                    _response.postValue(response.body())
                    if (parsed != null) {
                        val meal = Meal(
                            foodName = foodName,
                            measure = parsed.measure,
                            protein = response.body()!!.totalNutrients.get("PROCNT")?.quantity
                                ?: 0.0.formatToTwoHouses(),
                            carb = response.body()!!.totalNutrients.get("CHOCDF")?.quantity
                                ?: 0.0.formatToTwoHouses(),
                            fat = response.body()!!.totalNutrients.get("FAT")?.quantity
                                ?: 0.0.formatToTwoHouses(),
                            quantity = response.body()!!.totalWeight.formatToTwoHouses(),
                            calories = response.body()!!.calories,
                        )

                        _meal.postValue(meal)
                    } else {
                        Log.d("MyViewModel", "meal have null properties")
                    }
                } else {
                    Log.d("Tag", "teste")
                }
            } catch (exception: Exception) {
                throw exception
            }
        }
    }

    fun insertMealForTheList(meal: Meal, periodFood: String) {
        when (periodFood) {
            "Breakfast" -> _breakfast.value?.add(meal)
            "Lunch" -> _lunch.value?.add(meal)
            "Snack" -> _snack.value?.add(meal)
            "Dinner" -> _dinner.value?.add(meal)
        }
        Log.d("tag", "${_breakfast.value}")
        Log.d("tag", "${_lunch.value}")
        Log.d("tag", "${_snack.value}")
        Log.d("tag", "${_dinner.value}")
    }

    fun insertMeals(meal: Meal) = viewModelScope.launch {
        if (meal.foodName != repository.findByName(meal.foodName)?.foodName) {
            repository.insertAll(meal)
        }
    }

    suspend fun getFoodByFoodName(foodName: String) {
        _meal.postValue(repository.findByName(foodName))
    }

    suspend fun mealIsPresentInDatabase(foodName: String): Boolean {
        return repository.findByName(foodName) != null
    }

    fun noMeal() {
        _meal.value = Meal()
    }
}
