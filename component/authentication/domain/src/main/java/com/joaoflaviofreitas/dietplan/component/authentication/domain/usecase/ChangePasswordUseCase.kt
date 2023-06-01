package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface ChangePasswordUseCase {

    suspend fun execute(credential: AuthCredential, password: String): DataState<Boolean>
}