package com.example.recipe_app.network

import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import retrofit2.Response

class RemoteDataSourceImpl(
    private val apiService: ApiService
): RemoteDataSource {
    override suspend fun getRandomMeal(): Response<MealResponse> {
        return apiService.getRandomMeal()
    }

    override suspend fun getAllCategories(): Response<CategoriesResponse> {
        return apiService.getAllCategories()
    }

    override suspend fun filterByCategory(category: String): Response<MealResponse> {
        return apiService.filterByCategory(category)
    }
}