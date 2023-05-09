package com.joaoflaviofreitas.dietplan.di.storage

import com.joaoflaviofreitas.dietplan.component.storage.domain.FirebaseStorageRepository
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.DeleteImageProfileUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.DeleteImageProfileUseCaseImpl
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.SaveImageProfileUseCase
import com.joaoflaviofreitas.dietplan.component.storage.domain.usecase.SaveImageProfileUseCaseImpl
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
}
