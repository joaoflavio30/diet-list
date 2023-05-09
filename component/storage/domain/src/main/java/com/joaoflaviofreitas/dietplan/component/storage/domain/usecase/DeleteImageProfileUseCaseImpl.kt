package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState
import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository

class DeleteImageProfileUseCaseImpl(private val firebaseStorageRepository: FirebaseStorageRepository): DeleteImageProfileUseCase {
    override suspend fun execute(): DataState<Boolean> {
        return firebaseStorageRepository.deleteImageProfile()
    }
}
