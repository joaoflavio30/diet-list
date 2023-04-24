package com.example.dietplan.domain.usecase

import com.example.dietplan.FirebaseAuthRepository

class CheckUserAuthSignedInUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {
    suspend fun execute() = firebaseAuthRepository.userIsLogged()
}
