package com.joaoflaviofreitas.dietplan.component.authentication.data.di

import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.authentication.data.FirebaseAuthRepositoryImpl
import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository
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
    fun providesFirebaseAuthRepository(auth: FirebaseAuth): FirebaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth)
    }
}
