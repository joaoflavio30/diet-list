package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class ChangeEmailUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(credential: AuthCredential, email: String): DataState<Boolean> { Log.d("changeEmailUseCase", "chamado")
        return firebaseAuthRepository.changeEmail(credential, email)
    }
}
