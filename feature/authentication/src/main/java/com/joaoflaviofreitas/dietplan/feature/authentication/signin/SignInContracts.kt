package com.joaoflaviofreitas.dietplan.feature.authentication.signin

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth

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

        fun navigateToDailyGoalFragment()

        fun navigateToRestorePasswordFragment()

        fun navigateToHomeFragment()

        fun checkIfUserMakesDailyGoalObserver()
    }

    interface SignInViewModel {
        fun signIn(userAuth: UserAuth)

        fun signInWithGoogle(credential: AuthCredential)

        fun checkIfUserMakesDailyGoal(userEmail: String)
    }
}
