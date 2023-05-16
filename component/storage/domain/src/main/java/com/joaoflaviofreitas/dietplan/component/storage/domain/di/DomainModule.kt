package com.joaoflaviofreitas.dietplan.component.storage.domain.di

import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun providesDeleteImageProfileUseCase(repository: FirebaseStorageRepository): DeleteImageProfileUseCase = DeleteImageProfileUseCaseImpl(repository)

    @Singleton
    @Provides
    fun providesSaveImageProfileUseCase(repository: FirebaseStorageRepository):SaveImageProfileUseCase = SaveImageProfileUseCaseImpl(repository)

    @Singleton
    @Provides
    fun providesGetMetadataOfProfileImageUseCase(repository: FirebaseStorageRepository):GetMetadataOfProfileImageUseCase = GetMetadataOfProfileImageUseCaseImpl(repository)
}
