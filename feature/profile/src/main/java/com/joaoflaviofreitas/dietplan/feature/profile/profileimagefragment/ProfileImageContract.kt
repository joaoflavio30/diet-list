package com.joaoflaviofreitas.dietplan.feature.profile.profileimagefragment

interface ProfileImageContract {

    interface ProfileImageFragment {

        fun setOnClickListeners()

        fun navigateToProfileFragment()

        fun bindImage()
    }
}
