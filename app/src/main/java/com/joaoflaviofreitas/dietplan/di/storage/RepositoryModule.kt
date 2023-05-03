package com.joaoflaviofreitas.dietplan.di.storage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.dietplan.component.storage.data.FirebaseStorageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesFirebaseStorage() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun providesFirebaseStorageRepository(auth: FirebaseAuth, storage: FirebaseStorage) = FirebaseStorageRepositoryImpl(auth, storage)
}
