package com.joaoflaviofreitas.dietplan.data

import com.joaoflaviofreitas.dietplan.component.food.data.local.Dao
import com.joaoflaviofreitas.dietplan.component.food.data.remote.Api
import com.joaoflaviofreitas.dietplan.component.food.data.repository.MealRepositoryImpl
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

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
}
