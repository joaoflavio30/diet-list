package com.joaoflaviofreitas.dietplan.feature.profile


interface ProfileImageContract {

    interface ProfileImageFragment {

        fun setOnClickListeners()

        fun navigateToProfileFragment()

        fun bindImage()
    }
}
