package com.joaoflaviofreitas.dietplan.ui.authentication.restorepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.RestorePasswordByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestorePasswordViewModel @Inject constructor(private val restorePasswordByEmailUseCase: RestorePasswordByEmailUseCase):
    ViewModel(),
    RestoreContract.RestorePasswordViewModel {

    private val _progressBarRestore = MutableLiveData<Boolean>()
    val progressBarRestore get() = _progressBarRestore

    private val _restoreSuccess = MutableLiveData<DataState<Boolean>>()
    val restoreSuccess get() = _restoreSuccess

    override fun changePasswordByEmail(email: String) {
        viewModelScope.launch {
            when (val result = restorePasswordByEmailUseCase.execute(email)) {
                is DataState.Success -> { _restoreSuccess.postValue(DataState.Success(true))
                    _progressBarRestore.postValue(false)
                }
                is DataState.Error -> { _restoreSuccess.postValue(DataState.Error(result.exception))
                    _progressBarRestore.postValue(false)
                }
                is DataState.Loading -> { _progressBarRestore.postValue(true) }
            }
        }
    }
}
