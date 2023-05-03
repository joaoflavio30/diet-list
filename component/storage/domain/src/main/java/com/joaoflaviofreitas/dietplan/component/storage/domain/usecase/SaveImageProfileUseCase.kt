package com.joaoflaviofreitas.dietplan.component.storage.domain.usecase

import com.joaoflaviofreitas.dietplan.component.storage.domain.DataState

interface SaveImageProfileUseCase {
    suspend fun execute(uri: String): DataState<String>
}
