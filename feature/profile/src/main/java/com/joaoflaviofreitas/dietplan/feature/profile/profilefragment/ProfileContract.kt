package com.joaoflaviofreitas.dietplan.feature.profile.profilefragment

import android.net.Uri
import com.google.firebase.auth.AuthCredential

interface ProfileContract {

    interface ProfileFragment {

        fun checkFieldForEmail(): Boolean

        fun checkFieldForPassword(): Boolean

        fun setOnClickListener()

        fun changeEmail(credential: AuthCredential)

        fun changePassword(credential: AuthCredential)

        fun observeEmailChangeSuccess()

        fun observePasswordChangeSuccess()

        fun showToastLengthLong(text: String)

        fun bindFields()

        fun changeEmailOrPasswordWithAuthenticationUser(changeEmailOrPassword: String)

        fun navigateToProfileImageFragment()

        fun checkIfUserHasPermission(): Boolean
    }

    interface ProfileViewModel {

        fun changeEmail(credential: AuthCredential, email: String)

        fun changePassword(credential: AuthCredential, password: String)

        fun saveProfileImageInFirebaseStorage(uri: Uri?)

        fun deleteProfileImageInFirebaseStorage()

        fun saveProfileImageLiveData(uri: String)

        fun deleteProfileImageLiveData()

        suspend fun getMetadataOfProfileImage(): Long
    }
}