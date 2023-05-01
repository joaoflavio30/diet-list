package com.joaoflaviofreitas.dietplan.feature.home

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView

class MyDialog(activity: Activity, fragment: Fragment) : Dialog(activity) {
    init {
        val dialog = this@MyDialog
        dialog.setContentView(R.layout.food_dialog)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM
        dialog.window?.attributes = lp

        dialog.window?.setBackgroundDrawableResource(R.drawable.top_background)

        val breakfast = findViewById<MaterialCardView>(R.id.container_1)
        val lunch = findViewById<MaterialCardView>(R.id.container_2)
        val dinner = findViewById<MaterialCardView>(R.id.container_3)
        val opa = findViewById<MaterialCardView>(R.id.container_4)
        bindClickMeal(breakfast, fragment)
        bindClickMeal(lunch, fragment)
        bindClickMeal(dinner, fragment)
        bindClickMeal(opa, fragment)
    }
    fun bindClickMeal(view: View, fragment: Fragment) {
        view.setOnClickListener {
            when (view.id) {
                R.id.container_1 -> {
                    fragment.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }
                R.id.container_2 -> {
                    fragment.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }
                R.id.container_3 -> {
                    fragment.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }
                R.id.container_4 -> {
                    fragment.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                }
            }
            this@MyDialog.dismiss()
        }
    }
}
