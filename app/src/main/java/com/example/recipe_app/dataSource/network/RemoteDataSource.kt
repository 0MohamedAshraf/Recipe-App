package com.example.recipe_app.dataSource.network

import com.example.recipe_app.dataSource.network.dto.RandomMealResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getRandomMeal() : Response<RandomMealResponse>
}