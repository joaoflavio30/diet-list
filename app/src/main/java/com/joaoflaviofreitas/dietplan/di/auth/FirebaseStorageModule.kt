package com.joaoflaviofreitas.dietplan.di.auth

import com.joaoflaviofreitas.dietplan.feature.home.FirebaseImageUploader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FirebaseStorageModule {

    @Provides
    fun provideFirebaseImageUploader(): FirebaseImageUploader {
        return FirebaseImageUploader()
    }
}
