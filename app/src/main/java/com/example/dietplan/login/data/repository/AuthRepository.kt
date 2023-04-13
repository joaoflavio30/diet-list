package com.example.dietplan.login.data.repository

import com.example.dietplan.login.data.remote.FirebaseAuthLogin
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseSrc: FirebaseAuthLogin) {
    fun singInWithGoogle(account: GoogleSignInAccount) = firebaseSrc.signInWithGoogle(account)
}
