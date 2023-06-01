package com.joaoflaviofreitas.dietplan.ui.authentication.restorepassword

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
