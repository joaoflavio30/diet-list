package com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth

interface SignUpUseCase {
    suspend fun execute(userAuth: UserAuth): DataState<FirebaseUser>
}
