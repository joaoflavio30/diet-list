package com.joaoflaviofreitas.dietplan.ui.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.SignInUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.SignInWithGoogleUseCase
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.CheckIfDailyGoalExistsByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val checkIfDailyGoalExistsByEmailUseCase: CheckIfDailyGoalExistsByEmailUseCase,
) :
    ViewModel(),
    SignInContracts.SignInViewModel {

    private val _progressBarSignIn = MutableLiveData<Boolean>()
    val progressBarSignIn get() : LiveData<Boolean> = _progressBarSignIn

    private val _signInSuccess = MutableLiveData<DataState<Boolean>>()
    val signInSuccess get() : LiveData<DataState<Boolean>> = _signInSuccess

    private val _checkIfUserMakesDailyGoal = MutableLiveData<Boolean>()
    val checkIfUserMakesDailyGoal get() : LiveData<Boolean> = _checkIfUserMakesDailyGoal

    override fun signIn(userAuth: UserAuth) {
        viewModelScope.launch {
            when (val result = signInUseCase.execute(userAuth)) {
                is DataState.Loading -> _progressBarSignIn.postValue(true)
                is DataState.Error -> { _progressBarSignIn.postValue(false)
                    _signInSuccess.postValue(DataState.Error(result.exception))
                }
                is DataState.Success -> _signInSuccess.postValue(DataState.Success(true))
            }
        }
    }

    override fun signInWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            when (val result = signInWithGoogleUseCase.execute(credential)) {
                is DataState.Loading -> _progressBarSignIn.postValue(true)
                is DataState.Error -> { _progressBarSignIn.postValue(false)
                    _signInSuccess.postValue(DataState.Error(result.exception))
                }
                is DataState.Success -> _signInSuccess.postValue(DataState.Success(true))
            }
        }
    }

    override fun checkIfUserMakesDailyGoal(userEmail: String) {
        viewModelScope.launch {
            when (val result = checkIfDailyGoalExistsByEmailUseCase.execute(userEmail)) {
                true -> _checkIfUserMakesDailyGoal.postValue(true)
                false -> _checkIfUserMakesDailyGoal.postValue(false)
            }
        }
    }
}
