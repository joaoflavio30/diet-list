package com.joaoflaviofreitas.dietplan

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.navigation.NavController
import com.google.android.material.card.MaterialCardView

class MyDialog(activity: Activity, navController: NavController) : Dialog(activity) {
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
        val snack = findViewById<MaterialCardView>(R.id.container_3)
        val dinner = findViewById<MaterialCardView>(R.id.container_4)
        bindClickMeal(breakfast, navController)
        bindClickMeal(lunch, navController)
        bindClickMeal(snack, navController)
        bindClickMeal(dinner, navController)
    }
    private fun bindClickMeal(view: View, navController: NavController) {
        view.setOnClickListener {
            when (view.id) {
                R.id.container_1 -> {
                    navController.navigate(R.id.searchFragment)
                }
                R.id.container_2 -> {
                    navController.navigate(R.id.searchFragment)
                }
                R.id.container_3 -> {
                    navController.navigate(R.id.searchFragment)
                }
                R.id.container_4 -> {
                    navController.navigate(R.id.searchFragment)
                }
            }
            this@MyDialog.dismiss()
        }
    }
}
