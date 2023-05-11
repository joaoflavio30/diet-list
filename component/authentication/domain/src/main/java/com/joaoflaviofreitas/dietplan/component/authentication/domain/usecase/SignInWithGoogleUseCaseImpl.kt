package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class SignInWithGoogleUseCaseImpl(private val firebaseAuthRepository: FirebaseAuthRepository): SignInWithGoogleUseCase {

    override suspend fun execute(credential: AuthCredential): DataState<FirebaseUser> {
        return firebaseAuthRepository.signInWithGoogle(credential)
    }
}
