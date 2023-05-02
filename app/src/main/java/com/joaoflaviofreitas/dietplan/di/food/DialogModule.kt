package com.joaoflaviofreitas.dietplan.di.food

import android.app.Activity
import androidx.fragment.app.Fragment
import com.joaoflaviofreitas.dietplan.feature.home.MyDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object DialogModule {

    @Provides
    fun provideDialog(activity: Activity, fragment: Fragment): MyDialog {
        return MyDialog(activity, fragment)
    }
}
