package com.example.dietplan.presentation.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietplan.domain.model.DataState
import com.example.dietplan.domain.usecase.CheckUserAuthSignedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val checkUserAuthSignedInUseCase: CheckUserAuthSignedInUseCase) : ViewModel(), SplashScreenContract.SplashScreenViewModel {

    private val _signInSuccess = MutableLiveData<DataState<Boolean>>()
    val signInSuccess get() = _signInSuccess

    override fun checkUserAuthSignedIn() {
        viewModelScope.launch {
            _signInSuccess.postValue(checkUserAuthSignedInUseCase.execute())
        }
    }
}
