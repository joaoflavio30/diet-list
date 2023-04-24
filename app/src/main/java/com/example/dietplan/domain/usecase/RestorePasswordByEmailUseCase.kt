package com.example.dietplan.domain.usecase

import com.example.dietplan.FirebaseAuthRepository

class RestorePasswordByEmailUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(email: String) = firebaseAuthRepository.restorePasswordByEmail(email)
}
