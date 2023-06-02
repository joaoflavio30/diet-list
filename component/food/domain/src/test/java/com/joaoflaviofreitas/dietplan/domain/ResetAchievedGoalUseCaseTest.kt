package com.joaoflaviofreitas.dietplan.domain

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.ResetAchievedGoalUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.ResetAchievedGoalUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ResetAchievedGoalUseCaseTest {

    private lateinit var repository: MealRepository
    private lateinit var resetAchievedGoalUseCase: ResetAchievedGoalUseCase
    private val currentDate = "04/23/2023"
    private var objectDate = "04/24/2023"
    private val fakeEmail = "test@example.com"

    @Before
    fun setUp() {
        repository = mockk()
        resetAchievedGoalUseCase = ResetAchievedGoalUseCaseImpl(repository)
    }

    @Test
    fun `Verify if achievedGoal is reset`() {
        runBlocking {
            // Prepare
            val expectedAchievedGoal = AchievedGoal(userEmail = fakeEmail, date = currentDate, aerobic = false, id = 2)
            coEvery { repository.updateAchievedGoal(expectedAchievedGoal) } returns true

            resetAchievedGoalUseCase.execute(
                fakeEmail,
                currentDate,
                AchievedGoal(date = objectDate, id = 2),
            )

            assert(repository.updateAchievedGoal(expectedAchievedGoal))
        }
    }

    @Test
    fun `Verify if achievedGoal is no reset because currentDate == objectDate`() {
        runBlocking {
            // Prepare
            objectDate = currentDate
            val expectedAchievedGoal = AchievedGoal(userEmail = fakeEmail, date = objectDate, id = 2)
            coEvery { repository.updateAchievedGoal(expectedAchievedGoal) } returns true

            resetAchievedGoalUseCase.execute(
                fakeEmail,
                currentDate,
                AchievedGoal(userEmail = fakeEmail, date = objectDate, id = 2),
            )

            coVerify(exactly = 0) { repository.updateAchievedGoal(expectedAchievedGoal) }
        }
    }
}
