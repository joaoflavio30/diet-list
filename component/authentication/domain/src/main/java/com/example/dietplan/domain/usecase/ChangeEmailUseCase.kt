package com.example.dietplan.domain.usecase

import com.example.dietplan.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser

class ChangeEmailUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(currentUser: FirebaseUser, email: String) = firebaseAuthRepository.changeEmail(currentUser, email)
}
