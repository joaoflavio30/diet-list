package com.joaoflaviofreitas.dietplan.domain

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.GetAchievedGoalInDatabaseUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.GetAchievedGoalInDatabaseUseCaseImpl
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class GetAchievedGoalInDatabaseUseCaseTest {

    private val fakeEmail = "test@example.com"

    private lateinit var getAchievedGoal: GetAchievedGoalInDatabaseUseCase
    private lateinit var repository: MealRepository

    @Before
    fun setUp() {
        repository = mockk()
        getAchievedGoal = GetAchievedGoalInDatabaseUseCaseImpl(repository)
    }

    @Test
    fun `verify if useCase returns achievedGoal`() {
        runBlocking {
            coEvery { (repository.getAchievedGoal(fakeEmail)) }.returns(AchievedGoal(userEmail = fakeEmail, protein = 200.0))

            val result = getAchievedGoal.execute(fakeEmail)

            assert(result.protein == 200.0)
        }
    }

    @Test
    fun `Verify if useCase returns no achievedGoal`() {
        runBlocking {
            coEvery { repository.getAchievedGoal(fakeEmail) } throws Exception("Failed to get achievedGoal in database")

            assertThrows(Exception::class.java) {
                runBlocking {
                    getAchievedGoal.execute(fakeEmail)
                }
            }
        }
        coVerify { repository.getAchievedGoal(fakeEmail) }
        confirmVerified(repository)
    }

    @After
    fun teardown() {
        clearMocks(repository)
    }
}
