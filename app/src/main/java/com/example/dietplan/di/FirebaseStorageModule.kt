package com.example.dietplan.di

import com.example.dietplan.FirebaseImageUploader
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