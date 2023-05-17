package com.joaoflaviofreitas.dietplan.ui.splash

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

        fun checkIfUserMakesDailyGoal(userEmail: String)
    }
}
