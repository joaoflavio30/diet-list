package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface DeleteUserUseCase {
    suspend fun execute(): DataState<Boolean>
}
