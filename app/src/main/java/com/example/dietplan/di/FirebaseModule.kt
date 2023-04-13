package com.example.dietplan.di

import com.example.dietplan.login.data.remote.FirebaseAuthLogin
import com.example.dietplan.register.data.repository.remote.FirebaseAuthRegister
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthLogin(firebase: FirebaseAuth): FirebaseAuthLogin = FirebaseAuthLogin(firebase)

    @Provides
    @Singleton
    fun provideFirebaseAuthRegister(firebase: FirebaseAuth): FirebaseAuthRegister = FirebaseAuthRegister(firebase)
}
