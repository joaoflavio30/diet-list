package com.example.dietplan

import com.example.dietplan.domain.model.UserAuth
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepository {

    suspend fun signUp(userAuth: UserAuth): DataState<FirebaseUser>

    suspend fun signIn(userAuth: UserAuth): DataState<FirebaseUser>

    suspend fun signInWithGoogle(credential: AuthCredential): DataState<FirebaseUser>

    suspend fun userIsLogged(): DataState<Boolean>

    suspend fun restorePasswordByEmail(email: String): DataState<Boolean>

    suspend fun changePassword(currentUser: FirebaseUser, password: String): DataState<Boolean>

    suspend fun changeEmail(currentUser: FirebaseUser, email: String): DataState<Boolean>
}
