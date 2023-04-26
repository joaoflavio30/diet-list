package com.example.dietplan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dietplan.data.model.AchievedGoal
import com.example.dietplan.data.model.DailyGoal

@Database(entities = [Meal::class, DailyGoal::class, AchievedGoal::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): Dao
}
