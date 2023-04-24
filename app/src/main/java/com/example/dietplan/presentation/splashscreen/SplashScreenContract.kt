package com.example.dietplan.presentation.splashscreen

interface SplashScreenContract {

    interface SplashScreenFragment {

        fun navigateToSignInFragment()

        fun navigateToHomeFragment()

        fun signInSuccessObserver()

        fun navigateToDailyGoalFragment()
    }
    interface SplashScreenViewModel {
        fun checkUserAuthSignedIn()
    }
}
