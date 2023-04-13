package com.example.dietplan.repositories

import com.example.dietplan.DataState
import com.example.dietplan.register.data.repository.remote.FirebaseAuthRegister
import com.firebase.ui.auth.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val firebaseSrc: FirebaseAuthRegister) :
    RegisterRepository {
    fun signOutWithGoogle(email: String, password: String) = firebaseSrc.signOutWithGoogle(email, password)
    override suspend fun register(): Flow<DataState<User>> {
        TODO("Not yet implemented")
    }
}
