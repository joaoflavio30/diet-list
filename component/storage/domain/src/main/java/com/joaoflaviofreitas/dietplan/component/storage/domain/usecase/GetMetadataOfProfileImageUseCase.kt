package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

interface GetMetadataOfProfileImageUseCase {

    suspend fun execute(): Long
}
