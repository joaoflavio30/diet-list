package com.joaoflaviofreitas.dietplan.component.food.data.remote

import com.joaoflaviofreitas.dietplan.component.food.domain.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://edamam-edamam-nutrition-analysis.p.rapidapi.com"

interface Api {
    @GET("/api/nutrition-data")
    suspend fun getFoodInfo(
        @Query("ingr") ingr: String,
        @Query("nutrition-type") nutritionType: String = "logging",
    ): Response<ProductResponse>
}
