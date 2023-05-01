package com.joaoflaviofreitas.dietplan.component.food.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    val gsonConverterFactory = GsonConverterFactory.create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build().create(Api::class.java)
}