package com.joaoflaviofreitas.dietplan.component.food.data.remote

import com.joaoflaviofreitas.dietplan.component.food.domain.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.edamam.com"
const val APP_ID = "7f4cac62"
const val APP_KEY = "51740879a391522b10699c8a1f91eebc"

interface Api {

    @GET("/api/nutrition-data")
    suspend fun getFoodInfo(
        @Query("ingr") ingr: String,
        @Query("app_id") appId: String = APP_ID,
        @Query("app_key") appKey: String = APP_KEY,
        @Query("nutrition-type") nutritionType: String = "logging",
    ): Response<ProductResponse>
}
