package com.example.dietplan.fragments.contracts

import com.example.dietplan.domain.model.UserAuth

interface SignUpContract {

    interface SignUpFragment {
        fun setOnClickListener()

        fun progressBarSignUpObserver()

        fun checkFields(): Boolean

        fun signUpSuccessObserver()

        fun showProgressBarSignUp()

        fun hideProgressBarSignUp()

        fun showToastLengthLong(text: String)

        fun navigateToPopBackStack()

        fun navigateToSignIn()
    }

    interface SignUpViewModel {
        fun signUp(userAuth: UserAuth)
    }
}
