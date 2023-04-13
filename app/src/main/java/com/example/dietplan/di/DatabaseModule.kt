package com.example.dietplan.di

import android.content.Context
import androidx.room.Room
import com.example.dietplan.data.local.AppDatabase
import com.example.dietplan.data.local.Dao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    lateinit var database: AppDatabase

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MYDB",
        ).fallbackToDestructiveMigration()
            .build()
        return database
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): Dao = appDatabase.dao()
}
