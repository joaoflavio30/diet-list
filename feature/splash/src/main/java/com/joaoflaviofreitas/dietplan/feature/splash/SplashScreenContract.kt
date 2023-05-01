package com.joaoflaviofreitas.dietplan.feature.splash

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
