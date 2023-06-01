package com.joaoflaviofreitas.dietplan.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.*
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.TestCoroutineRule
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.getOrAwaitValue
import com.joaoflaviofreitas.dietplan.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getMealByApiUseCase = mock<GetMealByApiUseCase>()
    private val getMealByDatabaseUseCase = mock<GetMealByDatabaseUseCase>()
    private val checkIfHaveDataInDatabaseUseCase = mock<CheckIfHaveDataInDatabaseUseCase>()
    private val getDailyGoalInDatabaseUseCase = mock<GetDailyGoalInDatabaseUseCase>()
    private val saveAchievedGoalInDatabaseUseCase = mock<SaveAchievedGoalInDatabaseUseCase>()
    private val getAchievedGoalInDatabaseUseCase = mock<GetAchievedGoalInDatabaseUseCase>()
    private val updateAchievedGoalInDatabaseUseCase = mock<UpdateAchievedGoalInDatabaseUseCase>()

    private lateinit var viewModel: SearchViewModel
    private val email = "test@example.com"

    @Before
    fun setup() {
        val firebaseAuthMock = Mockito.mock(FirebaseAuth::class.java)

        val currentUserMock = Mockito.mock(FirebaseUser::class.java)

        `when`(firebaseAuthMock.currentUser).thenReturn(currentUserMock)

        `when`(currentUserMock.email).thenReturn("test@example.com")

        viewModel = SearchViewModel(
            firebaseAuthMock,
            getMealByApiUseCase,
            getMealByDatabaseUseCase,
            checkIfHaveDataInDatabaseUseCase,
            getDailyGoalInDatabaseUseCase,
            saveAchievedGoalInDatabaseUseCase,
            getAchievedGoalInDatabaseUseCase,
            updateAchievedGoalInDatabaseUseCase,
        )
    }

    @Test
    fun `Verify achievedGoal is found in database`() {
        testCoroutineRule.runBlockingTest {
            // Prepare
            whenever(getAchievedGoalInDatabaseUseCase.execute(email)).thenReturn(AchievedGoal(email))
            viewModel.getAchievedGoal()

            // Execute
            val achievedGoal = viewModel.loadAchievedGoal.getOrAwaitValue()

            // Verify
            assert(achievedGoal.userEmail == email)
        }
    }

    @Test
    fun `Verify if dailyGoal is found in database`() {
        testCoroutineRule.runBlockingTest {
            // Prepare
            whenever(getDailyGoalInDatabaseUseCase.execute(email)).thenReturn(DailyGoal(email))
            viewModel.getDailyDiet()

            // Execute
            val dailyGoal = viewModel.loadDailyGoal.getOrAwaitValue()

            // Verify
            assert(dailyGoal.userEmail == email)
        }
    }
}
