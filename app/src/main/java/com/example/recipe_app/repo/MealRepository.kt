package com.example.recipe_app.repo

import com.example.recipe_app.dataSource.network.dto.CategoriesResponse
import com.example.recipe_app.dataSource.network.dto.MealResponse
import retrofit2.Response

interface MealRepository {

    suspend fun getRandomMeal(): Response<MealResponse>

    suspend fun getAllCategories(): Response<CategoriesResponse>

    suspend fun filterByCategory(category: String) : Response<MealResponse>
}