package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class ChangePasswordUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(credential: AuthCredential, password: String) = firebaseAuthRepository.changePassword(credential, password)
}
