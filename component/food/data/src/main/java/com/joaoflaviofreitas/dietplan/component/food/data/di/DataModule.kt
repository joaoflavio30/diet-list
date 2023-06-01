package com.joaoflaviofreitas.dietplan.component.food.data.di

import android.content.Context
import androidx.room.Room
import com.joaoflaviofreitas.dietplan.component.food.data.local.AppDatabase
import com.joaoflaviofreitas.dietplan.component.food.data.local.Dao
import com.joaoflaviofreitas.dietplan.component.food.data.remote.Api
import com.joaoflaviofreitas.dietplan.component.food.data.remote.RetrofitService
import com.joaoflaviofreitas.dietplan.component.food.data.repository.MealRepositoryImpl
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MYDB",
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): Dao = appDatabase.dao()

    @Provides
    @Singleton
    fun providesMealRepository(database: Dao, remoteData: Api): MealRepository =
        MealRepositoryImpl(database, remoteData)

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = RetrofitService.gsonConverterFactory

    @Provides
    @Singleton
    fun provideRetrofitService(): Api =
        RetrofitService.retrofit
}
