package com.example.dietplan.home

interface HomeContract {

    interface HomeFragment {
        fun setOnClickListener()

        fun navigateToSearchFragment()

        fun bindData()

        fun showDialog()

        fun viewWaterMetrics()

        fun onClickMenuItem()

        fun loadImage()
    }
}
