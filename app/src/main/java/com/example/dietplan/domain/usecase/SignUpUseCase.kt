package com.example.dietplan.domain

import com.example.dietplan.FirebaseAuthRepository

class SignUpUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

   suspend fun invoke() = firebaseAuthRepository.signUp()

}