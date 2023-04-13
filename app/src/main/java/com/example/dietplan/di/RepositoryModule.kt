package com.example.dietplan.di

import com.example.dietplan.repositories.RegisterRepository
import com.example.dietplan.repositories.RegisterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository
}
