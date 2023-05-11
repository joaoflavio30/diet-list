package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class ChangeEmailUseCaseImpl(private val firebaseAuthRepository: FirebaseAuthRepository): ChangeEmailUseCase {

    override suspend fun execute(credential: AuthCredential, email: String): DataState<Boolean> {
        return firebaseAuthRepository.changeEmail(credential, email)
    }
}
