package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class SignUpUseCaseImpl(private val firebaseAuthRepository: FirebaseAuthRepository): SignUpUseCase {

    override suspend fun execute(userAuth: UserAuth): DataState<FirebaseUser> {
        return firebaseAuthRepository.signUp(userAuth)
    }
}
