package com.joaoflaviofreitas.dietplan.feature.authentication

import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState

interface RestoreContract {

    interface RestorePasswordFragment {
        fun setOnClickListener()

        fun navigateToPopBackStack()

        fun showToastLengthLong(text: String)

        fun validEmail(): Boolean

        fun showProgressBarRestore()

        fun hideProgressBarRestore()

        fun restoreSuccessObserver()

        fun progressBarRestoreObserver()
    }

    interface RestorePasswordViewModel {
        fun changePasswordByEmail(email: String)
    }
}
