package com.joaoflaviofreitas.dietplan.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.joaoflaviofreitas.dietplan.MyDialog
import com.joaoflaviofreitas.dietplan.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DialogModule {

//    @Provides
//    fun providesNavController(activity: Activity) = Navigation.findNavController(activity, R.id.nav_host_fragment)
//
//    @Provides
//    fun providesMyDialog(activity: Activity, navController: NavController) = MyDialog(activity, navController)
}
