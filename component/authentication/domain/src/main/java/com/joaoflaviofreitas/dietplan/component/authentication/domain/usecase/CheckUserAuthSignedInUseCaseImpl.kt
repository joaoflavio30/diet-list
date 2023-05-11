package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository

class CheckUserAuthSignedInUseCaseImpl(private val firebaseAuthRepository: FirebaseAuthRepository): CheckUserAuthSignedInUseCase {
    override suspend fun execute() = firebaseAuthRepository.userIsLogged()
}
