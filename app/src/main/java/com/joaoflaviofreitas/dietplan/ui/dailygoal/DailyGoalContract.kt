package com.joaoflaviofreitas.dietplan.ui.dailygoal

interface DailyGoalContract {

    interface DailyGoalFragment {
        fun checkFields(): Boolean

        fun setOnClickListener()

        fun submitDailyDiet()

        fun showToastLengthLong(text: String)

        fun navigateToHomeFragment()

        fun checkIfUserMakeDailyGoal()

        fun putTrueForPreferencesOfIfUserMakeDailyGoal()
    }
}
