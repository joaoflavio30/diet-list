package com.joaoflaviofreitas.dietplan.feature.splash

interface SplashScreenContract {

    interface SplashScreenFragment {

        fun navigateToSignInFragment()

        fun navigateToHomeFragment()

        fun signInSuccessObserver()

        fun navigateToDailyGoalFragment()

        fun checkIfUserMakesDailyGoalObserver()
    }
    interface SplashScreenViewModel {
        fun checkUserAuthSignedIn()

        fun checkIfUserMakesDailyGoal(userEmail: String): Boolean
    }
}
