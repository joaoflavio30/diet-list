package com.example.dietplan

import androidx.lifecycle.ViewModel
import com.example.dietplan.repositories.RegisterRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepositoryImpl) : ViewModel() {

    fun setupFirebaseAuthRegister(email: String, password: String) =
        repository.signOutWithGoogle(email, password)
}
