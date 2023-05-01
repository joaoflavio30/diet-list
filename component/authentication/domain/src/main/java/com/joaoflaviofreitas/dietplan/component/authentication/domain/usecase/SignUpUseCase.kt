package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class SignUpUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(userAuth: UserAuth): DataState<FirebaseUser> { Log.d("TAG", "sucess ")
        return firebaseAuthRepository.signUp(userAuth)
    }
}
