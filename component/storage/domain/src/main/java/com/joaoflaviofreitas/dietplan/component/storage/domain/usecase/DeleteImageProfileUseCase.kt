package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState

interface DeleteImageProfileUseCase {
    suspend fun execute(): DataState<Boolean>
}
