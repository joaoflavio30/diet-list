package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface SignOutUseCase {

    fun execute(): DataState<Boolean>
}
