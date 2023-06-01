package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

internal class SignInUseCaseImpl(private val firebaseAuthRepository: FirebaseAuthRepository): SignInUseCase {
    override suspend fun execute(userAuth: UserAuth): DataState<FirebaseUser> {
        return firebaseAuthRepository.signIn(userAuth)
    }
}
