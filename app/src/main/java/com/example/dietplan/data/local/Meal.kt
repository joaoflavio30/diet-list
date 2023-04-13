package com.example.dietplan.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "food_name") val foodName: String = "",
    @ColumnInfo(name = "measure") val measure: String = "",
    @ColumnInfo(name = "protein") val protein: Double = 0.0,
    @ColumnInfo(name = "carb") val carb: Double = 0.0,
    @ColumnInfo(name = "fat") val fat: Double = 0.0,
    @ColumnInfo(name = "quantity") val quantity: Double = 0.0,
    @ColumnInfo(name = "calories") val calories: Double = 0.0
)
