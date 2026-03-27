package com.example.recipe_app.dataSource.network

import com.example.recipe_app.dataSource.network.dto.RandomMealResponse
import retrofit2.Response

class RemoteDataSourceImpl(
    private val apiService: ApiService
): RemoteDataSource {
    override suspend fun getRandomMeal(): Response<RandomMealResponse> {
        return apiService.getRandomMeal()
    }
}