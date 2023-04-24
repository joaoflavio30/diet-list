package com.example.dietplan.di

import com.example.dietplan.FirebaseAuthRepository
import com.example.dietplan.FirebaseAuthRepositoryImpl
import com.example.dietplan.login.data.remote.FirebaseAuthLogin
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
    fun providesFirebaseAuthRepository(auth: FirebaseAuth): FirebaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth)
    }
}
