package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface SignInWithGoogleUseCase {
    suspend fun execute(credential: AuthCredential): DataState<FirebaseUser>
}
