package com.example.recipe_app.dataSource.network

import com.example.recipe_app.dataSource.network.dto.RandomMealResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal() : Response<RandomMealResponse>
}