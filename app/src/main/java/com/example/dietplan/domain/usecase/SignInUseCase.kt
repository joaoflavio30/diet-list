package com.example.dietplan.domain.usecase

import com.example.dietplan.DataState
import com.example.dietplan.FirebaseAuthRepository
import com.example.dietplan.domain.model.UserAuth
import com.google.firebase.auth.FirebaseUser

class SignInUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {
    suspend fun execute(userAuth: UserAuth): DataState<FirebaseUser> {
        return firebaseAuthRepository.signIn(userAuth)
    }
}
