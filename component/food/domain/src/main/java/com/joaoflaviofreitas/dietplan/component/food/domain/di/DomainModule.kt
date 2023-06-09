package com.joaoflaviofreitas.dietplan.component.food.domain.di

import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import com.joaoflaviofreitas.dietplan.component.food.domain.usecase.* // ktlint-disable no-wildcard-imports
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
    fun providesGetDailyGoalUseCase(): GetDailyGoalUseCase =
        GetDailyGoalUseCaseImpl()

    @Provides
    @Singleton
    fun providesGetMealByApiUseCase(repository: MealRepository): GetMealByApiUseCase =
        GetMealByApiUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesGetMealByDatabaseUseCase(repository: MealRepository): GetMealByDatabaseUseCase =
        GetMealByDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesSaveMealInDatabaseUseCase(repository: MealRepository): SaveMealInDatabaseUseCase =
        SaveMealInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesCheckIfHaveDataInDatabaseUseCase(repository: MealRepository): CheckIfHaveDataInDatabaseUseCase =
        CheckIfHaveDataInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesGetDailyGoalInDatabaseUseCase(repository: MealRepository): GetDailyGoalInDatabaseUseCase =
        GetDailyGoalInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesSaveDailyGoalInDatabaseUseCase(repository: MealRepository): SaveDailyGoalInDatabaseUseCase =
        SaveDailyGoalInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesUpdateDailyGoalInDatabaseUseCase(repository: MealRepository): UpdateDailyGoalInDatabaseUseCase =
        UpdateDailyGoalInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesGetAchievedGoalInDatabaseUseCase(repository: MealRepository): GetAchievedGoalInDatabaseUseCase =
        GetAchievedGoalInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesSaveAchievedGoalInDatabaseUseCase(repository: MealRepository): SaveAchievedGoalInDatabaseUseCase =
        SaveAchievedGoalInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesUpdateAchievedGoalInDatabaseUseCase(repository: MealRepository): UpdateAchievedGoalInDatabaseUseCase =
        UpdateAchievedGoalInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesAddWaterUseCase(repository: MealRepository): AddWaterUseCase =
        AddWaterUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesResetAchievedGoalUseCase(repository: MealRepository): ResetAchievedGoalUseCase = ResetAchievedGoalUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesCheckIfDailyGoalExistsByEmailUseCase(repository: MealRepository): CheckIfDailyGoalExistsByEmailUseCase = CheckIfDailyGoalExistsByEmailUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesAddAerobicAsDoneUseCase(repository: MealRepository): AddAerobicAsDoneUseCase = AddAerobicAsDoneUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesCheckIfHaveAchievedGoalInDatabaseUseCase(repository: MealRepository): CheckIfHaveAchievedGoalInDatabaseUseCase = CheckIfHaveAchievedGoalInDatabaseUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesResetDailyGoalUseCase(repository: MealRepository): ResetDailyGoalUseCase = ResetDailyGoalUseCaseImpl(repository)
}
