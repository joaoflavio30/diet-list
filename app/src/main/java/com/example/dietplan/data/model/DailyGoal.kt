package com.example.dietplan.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_goal")
data class DailyGoal(
    @ColumnInfo(name = "calories") var calories: Double = 0.0,
    @ColumnInfo(name = "protein") var protein: Double = 0.0,
    @ColumnInfo(name = "carb")var carb: Double = 0.0,
    @ColumnInfo(name = "fat")var fat: Double = 0.0,
    @ColumnInfo(name = "water")var water: Int = 0,
    @PrimaryKey(autoGenerate = false)var id: Int = 1,
)
