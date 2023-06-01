package com.joaoflaviofreitas.dietplan.component.food.data.remote

import com.joaoflaviofreitas.dietplan.component.food.domain.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.edamam.com"
const val APP_ID = "e01c0774"
const val APP_KEY = "18f3dcfd4be69061549c30c75c329987"

interface Api {

    @GET("/api/nutrition-data")
    suspend fun getFoodInfo(
        @Query("ingr") ingr: String,
        @Query("app_id") appId: String = APP_ID,
        @Query("app_key") appKey: String = APP_KEY,
        @Query("nutrition-type") nutritionType: String = "logging",
    ): Response<ProductResponse>
}
