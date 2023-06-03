package com.joaoflaviofreitas.dietplan.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.AddAerobicAsDoneUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.AddWaterUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.GetAchievedGoalInDatabaseUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.GetDailyGoalInDatabaseUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.ResetAchievedGoalUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.ResetDailyGoalUseCase
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.TestCoroutineRule
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.getOrAwaitValue
import com.joaoflaviofreitas.dietplan.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    companion object {
        const val EMAIL_TEST = "test@example.com"
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getAchievedGoalInDatabaseUseCase = mock<GetAchievedGoalInDatabaseUseCase>()
    private val getDailyGoalInDatabaseUseCase = mock<GetDailyGoalInDatabaseUseCase>()
    private val addWaterUseCase = mock<AddWaterUseCase>()
    private val resetAchievedGoalUseCase = mock<ResetAchievedGoalUseCase>()
    private val addAerobicAsDoneUseCase = mock<AddAerobicAsDoneUseCase>()
    private val resetDailyGoalUseCase = mock<ResetDailyGoalUseCase>()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        val firebaseAuthMock = Mockito.mock(FirebaseAuth::class.java)

        val currentUserMock = Mockito.mock(FirebaseUser::class.java)

        `when`(firebaseAuthMock.currentUser).thenReturn(currentUserMock)

        `when`(currentUserMock.email).thenReturn(EMAIL_TEST)

        viewModel = HomeViewModel(
            firebaseAuthMock,
            getAchievedGoalInDatabaseUseCase,
            getDailyGoalInDatabaseUseCase,
            addWaterUseCase,
            resetAchievedGoalUseCase,
            addAerobicAsDoneUseCase,
            resetDailyGoalUseCase,
        )
    }

    @Test
    fun `Verify achievedGoal is found in database`() {
        testCoroutineRule.runBlockingTest {
            // Prepare
            whenever(getAchievedGoalInDatabaseUseCase.execute(EMAIL_TEST)).thenReturn(
                AchievedGoal(
                    EMAIL_TEST,
                ),
            )
            viewModel.getAchievedGoal()

            // Execute
            val achievedGoal = viewModel.loadAchievedGoal.getOrAwaitValue()

            // Verify
            assert(achievedGoal.userEmail == EMAIL_TEST)
        }
    }
}
