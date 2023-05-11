package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class RestorePasswordByEmailUseCaseImpl(private val firebaseAuthRepository: FirebaseAuthRepository): RestorePasswordByEmailUseCase {

    override suspend fun execute(email: String) = firebaseAuthRepository.restorePasswordByEmail(email)
}
