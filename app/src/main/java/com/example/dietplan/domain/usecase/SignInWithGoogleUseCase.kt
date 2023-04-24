package com.example.dietplan.domain.usecase

import com.example.dietplan.DataState
import com.example.dietplan.FirebaseAuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

class SignInWithGoogleUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(credential: AuthCredential): DataState<FirebaseUser> {
        return firebaseAuthRepository.signInWithGoogle(credential)
    }
}
