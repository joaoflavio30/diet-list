package com.joaoflaviofreitas.dietplan.di.auth

import com.joaoflaviofreitas.dietplan.component.authentication.domain.repository.FirebaseAuthRepository
import com.joaoflaviofreitas.dietplan.component.authentication.domain.usecase.*
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
        SignUpUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun providesSignInUseCase(authRepository: FirebaseAuthRepository): SignInUseCase =
        SignInUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun providesSignInWithGoogleUseCase(authRepository: FirebaseAuthRepository): SignInWithGoogleUseCase =
        SignInWithGoogleUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun providesCheckUserAuthSignedInUseCase(authRepository: FirebaseAuthRepository): CheckUserAuthSignedInUseCase =
        CheckUserAuthSignedInUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun providesRestorePasswordByEmail(authRepository: FirebaseAuthRepository): RestorePasswordByEmailUseCase = RestorePasswordByEmailUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun providesChangeEmailUseCase(authRepository: FirebaseAuthRepository): ChangeEmailUseCase = ChangeEmailUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun providesChangePasswordUseCase(authRepository: FirebaseAuthRepository):ChangePasswordUseCase = ChangePasswordUseCaseImpl(authRepository)
}
