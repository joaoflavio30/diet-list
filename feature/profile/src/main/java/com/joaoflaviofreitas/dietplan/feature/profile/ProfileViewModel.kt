package com.joaoflaviofreitas.dietplan.feature.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.ChangeEmailUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val changeEmailUseCase: ChangeEmailUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
) : ViewModel(), ProfileContract.ProfileViewModel {

    private val _changeSuccessObserver = MutableLiveData<DataState<Boolean>>()
    val changeSuccessObserver = _changeSuccessObserver

    override fun changeEmail(credential: AuthCredential, email: String) {
        viewModelScope.launch {
            when (val result = changeEmailUseCase.execute(credential, email)) {
                is DataState.Success -> _changeSuccessObserver.postValue(DataState.Success(true))
                is DataState.Error -> _changeSuccessObserver.postValue(DataState.Error(result.exception))
                is DataState.Loading -> {}
            }
        }
    }

    override fun changePassword(credential: AuthCredential, password: String) {
        viewModelScope.launch {
            when (val result = changePasswordUseCase.execute(credential, password)) {
                is DataState.Success -> _changeSuccessObserver.postValue(DataState.Success(true))
                is DataState.Error -> _changeSuccessObserver.postValue(DataState.Error(result.exception))
                is DataState.Loading -> {}
            }
        }
    }
}
