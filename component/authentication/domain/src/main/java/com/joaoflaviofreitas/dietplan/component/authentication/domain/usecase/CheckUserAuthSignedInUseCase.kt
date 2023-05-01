package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class CheckUserAuthSignedInUseCase(private val firebaseAuthRepository: FirebaseAuthRepository) {
    suspend fun execute() = firebaseAuthRepository.userIsLogged()
}
