package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository

class GetMetadataOfProfileImageUseCaseImpl(private val repository: FirebaseStorageRepository): GetMetadataOfProfileImageUseCase {
    override suspend fun execute(): Long = repository.getMetadataOfImage()
}
