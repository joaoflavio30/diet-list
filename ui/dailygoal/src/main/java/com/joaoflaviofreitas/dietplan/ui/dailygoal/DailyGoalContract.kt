package com.joaoflaviofreitas.dietplan.ui.dailygoal

import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal

interface DailyGoalContract {

    interface DailyGoalFragment {
        fun checkFields(): Boolean

        fun setOnClickListener()

        fun submitDailyDiet()

        fun showToastLengthLong(text: String)

        fun navigateToHomeFragment()

        fun checkIfUserMakesDailyGoal()

        fun putTrueForPreferencesOfIfUserMakeDailyGoal()
    }

    interface DailyGoalViewModel {
        fun submitDailyDiet(nutrients: DailyGoal)

        fun submitAchievedGoal(achievedGoal: AchievedGoal)

        fun checkIfUserMakesDailyGoal(userEmail: String)
    }
}
