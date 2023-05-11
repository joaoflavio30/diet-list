package com.joaoflaviofreitas.dietplan.feature.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.CheckUserAuthSignedInUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.CheckUserAuthSignedInUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val checkUserAuthSignedInUseCaseImpl: CheckUserAuthSignedInUseCase) :
    ViewModel(),
    SplashScreenContract.SplashScreenViewModel {

    private val _signInSuccess = MutableLiveData<DataState<Boolean>>()
    val signInSuccess get() = _signInSuccess

    override fun checkUserAuthSignedIn() {
        viewModelScope.launch {
            _signInSuccess.postValue(checkUserAuthSignedInUseCaseImpl.execute())
        }
    }
}
