package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState
import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository

class SaveImageProfileUseCaseImpl(private val firebaseStorageRepository: FirebaseStorageRepository): SaveImageProfileUseCase {
    override suspend fun execute(uri: String): DataState<String> {
        return firebaseStorageRepository.saveImageProfile(uri)
    }
}
