package com.joaoflaviofreitas.dietplan.feature.profile

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface ProfileContract {

    interface ProfileFragment {

        fun checkFieldForEmail(): Boolean

        fun checkFieldForPassword(): Boolean

        fun setOnClickListener()

        fun changeEmail(credential: AuthCredential)

        fun observeEmailChangeSuccess()

        fun observePasswordChangeSuccess()

        fun showToastLengthLong(text: String)

        fun bindFields()
    }

    interface ProfileViewModel {

        fun changeEmail(credential: AuthCredential, email: String)

        fun changePassword(credential: AuthCredential, password: String)
    }
}
