package com.example.dietplan.register.data.repository.remote

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthRegister(private val firebaseAuth: FirebaseAuth) {
    val user = firebaseAuth.currentUser
    fun signOutWithGoogle(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)
}
