package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class SignOutUseCaseImpl(private val repository: FirebaseAuthRepository): SignOutUseCase {
    override fun execute(): DataState<Boolean> {
        return repository.signOut()
    }
}