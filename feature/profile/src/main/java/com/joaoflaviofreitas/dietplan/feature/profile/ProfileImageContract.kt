package com.joaoflaviofreitas.dietplan.feature.profile

import android.net.Uri

interface ProfileImageContract {

    interface ProfileImageFragment {
        fun cropImage(data: Uri?)

        fun deleteUserImageView()

        fun setOnClickListeners()

        fun navigateToProfileFragment()

        fun bindImage()
    }
}
