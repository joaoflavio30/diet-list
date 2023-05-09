package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

import android.net.Uri
import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState
import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository

class SaveImageProfileUseCaseImpl(private val firebaseStorageRepository: FirebaseStorageRepository): SaveImageProfileUseCase {
    override suspend fun execute(uri: Uri?): DataState<Uri?> {
        return firebaseStorageRepository.saveImageProfile(uri)
    }
}
