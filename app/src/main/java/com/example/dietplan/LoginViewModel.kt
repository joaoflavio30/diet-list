package com.example.dietplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietplan.domain.model.UserAuth
import com.example.dietplan.domain.usecase.SignInUseCase
import com.example.dietplan.domain.usecase.SignInWithGoogleUseCase
import com.example.dietplan.fragments.contracts.SignInContracts
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val signInUseCase: SignInUseCase, private val signInWithGoogleUseCase: SignInWithGoogleUseCase) : ViewModel(), SignInContracts.SignInViewModel {

    private val _progressBarSignIn = MutableLiveData<Boolean>()
    val progressBarSignIn get() : LiveData<Boolean> = _progressBarSignIn

    private val _signInSuccess = MutableLiveData<DataState<Boolean>>()
    val signInSuccess get() : LiveData<DataState<Boolean>> = _signInSuccess

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
}
