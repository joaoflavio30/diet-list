package com.joaoflaviofreitas.dietplan.data

import com.joaoflaviofreitas.dietplan.component.food.data.local.Dao
import com.joaoflaviofreitas.dietplan.component.food.data.remote.Api
import com.joaoflaviofreitas.dietplan.component.food.data.repository.MealRepositoryImpl
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.ProductResponse
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MealRepositoryTest {

    private lateinit var dao: Dao
    private lateinit var api: Api
    private val fakeEmail = "test@example.com"
    private lateinit var repository: MealRepository

    @Before
    fun setUp() {
        dao = mockk()
        api = mockk()
        repository = MealRepositoryImpl(dao, api)
    }

    @Test
    fun `Verify if updateAchievedGoal returns true`() {
        val achievedGoal = AchievedGoal(userEmail = fakeEmail, id = 2)
        runBlocking {
            coEvery { dao.updateAchievedGoal(achievedGoal) } just runs

            val result = repository.updateAchievedGoal(achievedGoal)

            coVerify { dao.updateAchievedGoal(achievedGoal) }
            assert(result)
        }
    }

    @Test
    fun `Verify if updateAchievedGoal returns false`() {
        val achievedGoal = AchievedGoal(userEmail = fakeEmail, id = 2)
        runBlocking {
            coEvery { dao.updateAchievedGoal(achievedGoal) } throws Exception()

            val result = repository.updateAchievedGoal(achievedGoal)

            coVerify { dao.updateAchievedGoal(achievedGoal) }
            assert(!result)
        }
    }

    @Test
    fun `Verify if Api returns nutrients`() {
        val ingredients = RequestFood("Rice", 1.0, "kg")
        val mockedResponse: Response<ProductResponse> = mockk()
        runBlocking {
            coEvery { api.getFoodInfo(any()) } returns mockedResponse

            val result = repository.getNutrientsByApi(ingredients)

            assert(result == mockedResponse)
        }
    }

    @Test
    fun `Verify if save meal in database is success`() {
        val meal = Meal(2, "Rice", protein = 200.2)
        runBlocking {
            coEvery { dao.insertAll(any()) } just runs

            repository.saveMealInDatabase(meal)

            coVerify { dao.insertAll(meal) }
        }
    }
}
