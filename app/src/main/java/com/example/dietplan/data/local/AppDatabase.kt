package com.example.dietplan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [com.example.dietplan.data.local.Meal::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): com.example.dietplan.data.local.Dao
}
