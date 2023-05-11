package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface RestorePasswordByEmailUseCase {

    suspend fun execute(email: String): DataState<Boolean>
}
