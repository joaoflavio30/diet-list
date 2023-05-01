package com.joaoflaviofreitas.dietplan.di

import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.CheckUserAuthSignedInUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.SignInUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.SignInWithGoogleUseCase
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun providesSignUpUseCase(authRepository: FirebaseAuthRepository): SignUpUseCase =
        SignUpUseCase(authRepository)

    @Provides
    @Singleton
    fun providesSignInUseCase(authRepository: FirebaseAuthRepository): SignInUseCase =
        SignInUseCase(authRepository)

    @Provides
    @Singleton
    fun providesSignInWithGoogleUseCase(authRepository: FirebaseAuthRepository): SignInWithGoogleUseCase =
        SignInWithGoogleUseCase(authRepository)

    @Provides
    @Singleton
    fun providesCheckUserAuthSignedInUseCase(authRepository: FirebaseAuthRepository): CheckUserAuthSignedInUseCase =
        CheckUserAuthSignedInUseCase(authRepository)
}
