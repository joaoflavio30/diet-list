package com.joaoflaviofreitas.dietplan.component.food.data.remote

import com.joaoflaviofreitas.dietplan.component.food.domain.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = "https://edamam-edamam-nutrition-analysis.p.rapidapi.com"
const val APP_ID = "edamam-edamam-nutrition-analysis.p.rapidapi.com"
const val APP_KEY = "d2d328e09dmsh93392ae57f386c2p137884jsn8fa35c8824b6"

interface Api {

    @GET("/api/nutrition-data")
    @Headers(
        "X-RapidAPI-Key: d2d328e09dmsh93392ae57f386c2p137884jsn8fa35c8824b6",
        "X-RapidAPI-Host: edamam-edamam-nutrition-analysis.p.rapidapi.com",
    )
    suspend fun getFoodInfo(
        @Query("ingr") ingr: String,
        @Query("nutrition-type") nutritionType: String = "logging",
    ): Response<ProductResponse>
}
