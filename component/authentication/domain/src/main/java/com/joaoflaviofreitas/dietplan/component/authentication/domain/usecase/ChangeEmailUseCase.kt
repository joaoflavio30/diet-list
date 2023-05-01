package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class ChangeEmailUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(currentUser: FirebaseUser, email: String) = firebaseAuthRepository.changeEmail(currentUser, email)
}
