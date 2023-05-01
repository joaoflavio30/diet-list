package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class RestorePasswordByEmailUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {

    suspend fun execute(email: String) = firebaseAuthRepository.restorePasswordByEmail(email)
}
