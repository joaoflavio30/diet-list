package com.example.dietplan.domain.usecase

import android.util.Log
import com.example.dietplan.DataState
import com.example.dietplan.FirebaseAuthRepository
import com.example.dietplan.domain.model.UserAuth
import com.google.firebase.auth.FirebaseUser

class SignUpUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(userAuth: UserAuth):DataState<FirebaseUser> { Log.d("TAG", "sucess ")
        return firebaseAuthRepository.signUp(userAuth)
    }
}
