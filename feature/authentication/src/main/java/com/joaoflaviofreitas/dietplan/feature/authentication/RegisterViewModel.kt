package com.joaoflaviofreitas.dietplan.feature.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val useCase: SignUpUseCase) :
    ViewModel(),
    SignUpContract,
    SignUpContract.SignUpViewModel {

    private val _progressBarSignUp = MutableLiveData<Boolean>()
    val progressBarSignUp: LiveData<Boolean> = _progressBarSignUp

    private val _signUpSuccess = MutableLiveData<DataState<Boolean>>()
    val signUpSuccess: LiveData<DataState<Boolean>> = _signUpSuccess

    override fun signUp(userAuth: UserAuth) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = useCase.execute(userAuth)) {
                is DataState.Loading -> _progressBarSignUp.postValue(true)
                is DataState.Error -> { _progressBarSignUp.postValue(false)
                    _signUpSuccess.postValue(DataState.Error(result.exception))
                }
                is DataState.Success -> _signUpSuccess.postValue(DataState.Success(true))
            }
        }
    }
}
