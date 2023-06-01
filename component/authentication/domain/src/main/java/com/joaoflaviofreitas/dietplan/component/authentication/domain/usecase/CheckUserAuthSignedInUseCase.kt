package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface CheckUserAuthSignedInUseCase {

    suspend fun execute(): DataState<Boolean>
}
