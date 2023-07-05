package com.joaoflaviofreitas.dietplan.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.ChangeEmailUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.ChangePasswordUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.DeleteUserUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.SignOutUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.DeleteImageProfileUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.GetMetadataOfProfileImageUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.SaveImageProfileUseCase
import com.joaoflaviofreitas.dietplan.ui.profile.profilefragment.ProfileContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val changeEmailUseCase: ChangeEmailUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val saveImageProfileUseCase: SaveImageProfileUseCase,
    private val deleteImageProfileUseCase: DeleteImageProfileUseCase,
    private val getMetadataOfProfileImageUseCase: GetMetadataOfProfileImageUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,

) : ViewModel(), ProfileContract.ProfileViewModel {

    private val _profileImage = MutableLiveData("")

    private val _changeSuccessObserver = MutableLiveData<DataState<Boolean>>()
    val changeSuccessObserver: LiveData<DataState<Boolean>> = _changeSuccessObserver

    private val _deleteImageSuccessObserver = MutableLiveData<DataState<Boolean>>()

    val userDeletedSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private var job: Job = Job()

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

    override fun signOut(): DataState<Boolean> {
        return when (val result = signOutUseCase.execute()) {
            is DataState.Success -> DataState.Success(true)
            is DataState.Error -> DataState.Error(result.exception)
            is DataState.Loading -> { DataState.Loading }
        }
    }

    override fun deleteUser() {
        viewModelScope.launch {
            when (val result = deleteUserUseCase.execute()) {
                is DataState.Success -> userDeletedSuccess.postValue(true)
                is DataState.Error -> userDeletedSuccess.postValue(false)
                else -> {}
            }
        }
    }
}
