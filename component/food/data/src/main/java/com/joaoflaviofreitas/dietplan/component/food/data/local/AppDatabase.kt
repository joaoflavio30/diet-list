package com.joaoflaviofreitas.dietplan.component.food.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal

@Database(entities = [Meal::class, DailyGoal::class, AchievedGoal::class], version = 15, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    internal abstract fun dao(): Dao
}
