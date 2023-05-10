package com.joaoflaviofreitas.dietplan.feature.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.ChangeEmailUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.ChangePasswordUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.DeleteImageProfileUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.GetMetadataOfProfileImageUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.SaveImageProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val changeEmailUseCase: ChangeEmailUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val saveImageProfileUseCase: SaveImageProfileUseCase,
    private val deleteImageProfileUseCase: DeleteImageProfileUseCase,
    private val getMetadataOfProfileImageUseCase: GetMetadataOfProfileImageUseCase,

) : ViewModel(), ProfileContract.ProfileViewModel {

    private val _profileImage = MutableLiveData("")
    val profileImage get() = _profileImage

    private val _changeSuccessObserver = MutableLiveData<DataState<Boolean>>()
    val changeSuccessObserver: LiveData<DataState<Boolean>> = _changeSuccessObserver

    private val _deleteImageSuccessObserver = MutableLiveData<DataState<Boolean>>()
    val deleteImageSuccessObserver get() = _deleteImageSuccessObserver

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

    override fun saveProfileImageInFirebaseStorage(uri: Uri?) {
        viewModelScope.launch {
            saveImageProfileUseCase.execute(uri)
        }
    }

    override fun deleteProfileImageInFirebaseStorage() {
        viewModelScope.launch {
            when (val result = deleteImageProfileUseCase.execute()) {
                is com.joaoflaviofreitas.dietplan.component.storage.domain.DataState.Success -> _deleteImageSuccessObserver.postValue(DataState.Success(true))
                is com.joaoflaviofreitas.dietplan.component.storage.domain.DataState.Error -> _deleteImageSuccessObserver.postValue(DataState.Error(result.exception))
                is com.joaoflaviofreitas.dietplan.component.storage.domain.DataState.Loading -> {}
            }
        }
    }

    override fun saveProfileImageLiveData(uri: String) {
        if (uri.isNotEmpty()) _profileImage.postValue(uri)
    }

    override fun deleteProfileImageLiveData() {
        _profileImage.postValue("")
    }

    override suspend fun getMetadataOfProfileImage(): Long {
        return getMetadataOfProfileImageUseCase.execute()
    }
}
