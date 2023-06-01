package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface ChangeEmailUseCase {

    suspend fun execute(credential: AuthCredential, email: String): DataState<Boolean>
}
