package com.example.dietplan

import androidx.lifecycle.ViewModel
import com.example.dietplan.login.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) =
        repository.singInWithGoogle(account)
}
