package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class DeleteUserUseCaseImpl(private val repository: FirebaseAuthRepository): DeleteUserUseCase {
    override suspend fun execute(): DataState<Boolean> =
        repository.deleteUser()
}
