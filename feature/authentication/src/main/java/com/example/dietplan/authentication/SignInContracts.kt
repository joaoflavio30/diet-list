package com.example.dietplan.authentication

import com.example.dietplan.domain.model.UserAuth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential

interface SignInContracts {
    interface SignInFragment {

        fun checkFields(): Boolean

        fun setOnClickListener()

        fun signInSuccessObserver()

        fun showProgressBarSignIn()

        fun hideProgressBarSignIn()

        fun showToastLengthLong(text: String)

        fun navigateToPopBackStack()

        fun navigateToSignUp()

        fun handleSignInResult(task: Task<GoogleSignInAccount>)

        fun initGoogleSignInClient()

        fun progressBarSignInObserver()

        fun navigateToHomeFragment()
    }

    interface SignInViewModel {
        fun signIn(userAuth: UserAuth)

        fun signInWithGoogle(credential: AuthCredential)
    }
}
