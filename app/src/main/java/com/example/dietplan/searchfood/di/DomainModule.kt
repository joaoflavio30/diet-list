package com.example.dietplan.searchfood.di

import com.example.dietplan.repositories.MealRepository
import com.example.dietplan.searchfood.usecases.* // ktlint-disable no-wildcard-imports
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
    fun providesGetDailyGoalUseCase(): GetDailyGoalUseCase = GetDailyGoalUseCaseImpl()

    @Provides
    @Singleton
    fun providesGetMealByApiUseCase(repository: MealRepository): GetMealByApiUseCase = GetMealByApiUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesGetMealByDatabaseUseCase(repository: MealRepository): GetMealByDatabaseUseCase = GetMealByDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesSaveMealInDatabaseUseCase(repository: MealRepository): SaveMealInDatabaseUseCase = SaveMealInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesCheckIfHaveDataInDatabaseUseCase(repository: MealRepository): CheckIfHaveDataInDatabaseUseCase = CheckIfHaveDataInDatabaseUseCaseImpl(repository)
}
