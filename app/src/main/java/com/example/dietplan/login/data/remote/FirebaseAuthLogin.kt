package com.example.dietplan.login.data.remote

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuthLogin(private val firebaseAuth: FirebaseAuth) {
    fun signInWithGoogle(account: GoogleSignInAccount) =
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))
}
