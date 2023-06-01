package com.joaoflaviofreitas.dietplan.ui.profile.profileimagefragment

interface ProfileImageContract {

    interface ProfileImageFragment {

        fun setOnClickListeners()

        fun navigateToProfileFragment()

        fun bindImage()
    }
}
