package com.joaoflaviofreitas.dietplan.component.food.data.di

import android.content.Context
import androidx.room.Room
import com.joaoflaviofreitas.dietplan.component.food.data.BuildConfig
import com.joaoflaviofreitas.dietplan.component.food.data.local.AppDatabase
import com.joaoflaviofreitas.dietplan.component.food.data.local.Dao
import com.joaoflaviofreitas.dietplan.component.food.data.remote.Api
import com.joaoflaviofreitas.dietplan.component.food.data.remote.ApiKeyInterceptor
import com.joaoflaviofreitas.dietplan.component.food.data.remote.BASE_URL
import com.joaoflaviofreitas.dietplan.component.food.data.repository.MealRepositoryImpl
import com.joaoflaviofreitas.dietplan.component.food.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor(BuildConfig.API_EDAMAN_KEY, BuildConfig.API_EDAMAN_HOST)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor) : OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(Api::class.java)
}
