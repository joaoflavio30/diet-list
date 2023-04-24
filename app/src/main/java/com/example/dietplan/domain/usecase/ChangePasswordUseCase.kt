package com.example.dietplan.domain.usecase

import com.example.dietplan.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser

class ChangePasswordUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(currentUser: FirebaseUser, password: String) = firebaseAuthRepository.changePassword(currentUser, password)
}
