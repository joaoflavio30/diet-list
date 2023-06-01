package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

internal class ChangePasswordUseCaseImpl(private val firebaseAuthRepository: FirebaseAuthRepository): ChangePasswordUseCase {

    override suspend fun execute(credential: AuthCredential, password: String) = firebaseAuthRepository.changePassword(credential, password)
}
