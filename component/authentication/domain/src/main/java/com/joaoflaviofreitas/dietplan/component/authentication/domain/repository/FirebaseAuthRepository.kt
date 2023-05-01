package com.joaoflaviofreitas.dietplan.component.authentication.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth

interface FirebaseAuthRepository {

    suspend fun signUp(userAuth: UserAuth): DataState<FirebaseUser>

    suspend fun signIn(userAuth: UserAuth): DataState<FirebaseUser>

    suspend fun signInWithGoogle(credential: AuthCredential): DataState<FirebaseUser>

    suspend fun userIsLogged(): DataState<Boolean>

    suspend fun restorePasswordByEmail(email: String): DataState<Boolean>

    suspend fun changePassword(currentUser: FirebaseUser, password: String): DataState<Boolean>

    suspend fun changeEmail(currentUser: FirebaseUser, email: String): DataState<Boolean>
}
